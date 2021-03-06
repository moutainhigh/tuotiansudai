package com.tuotiansudai.paywrapper.service.impl;

import com.google.common.base.Predicate;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.tuotiansudai.client.MQWrapperClient;
import com.tuotiansudai.dto.*;
import com.tuotiansudai.enums.*;
import com.tuotiansudai.exception.AmountTransferException;
import com.tuotiansudai.membership.service.MembershipPrivilegePurchaseService;
import com.tuotiansudai.message.*;
import com.tuotiansudai.mq.client.model.MessageQueue;
import com.tuotiansudai.paywrapper.client.PayAsyncClient;
import com.tuotiansudai.paywrapper.client.PaySyncClient;
import com.tuotiansudai.paywrapper.exception.PayException;
import com.tuotiansudai.paywrapper.repository.mapper.InvestTransferNotifyRequestMapper;
import com.tuotiansudai.paywrapper.repository.mapper.ProjectTransferMapper;
import com.tuotiansudai.paywrapper.repository.mapper.ProjectTransferNopwdMapper;
import com.tuotiansudai.paywrapper.repository.mapper.ProjectTransferNotifyMapper;
import com.tuotiansudai.paywrapper.repository.model.NotifyProcessStatus;
import com.tuotiansudai.paywrapper.repository.model.async.callback.BaseCallbackRequestModel;
import com.tuotiansudai.paywrapper.repository.model.async.callback.InvestNotifyRequestModel;
import com.tuotiansudai.paywrapper.repository.model.async.callback.ProjectTransferNotifyRequestModel;
import com.tuotiansudai.paywrapper.repository.model.async.request.ProjectTransferNopwdRequestModel;
import com.tuotiansudai.paywrapper.repository.model.async.request.ProjectTransferRequestModel;
import com.tuotiansudai.paywrapper.repository.model.sync.response.ProjectTransferNopwdResponseModel;
import com.tuotiansudai.paywrapper.repository.model.sync.response.ProjectTransferResponseModel;
import com.tuotiansudai.paywrapper.service.InvestTransferPurchaseService;
import com.tuotiansudai.repository.mapper.*;
import com.tuotiansudai.repository.model.*;
import com.tuotiansudai.rest.client.mapper.UserMapper;
import com.tuotiansudai.util.AmountConverter;
import com.tuotiansudai.util.IdGenerator;
import com.tuotiansudai.util.InterestCalculator;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.*;

@Service
public class InvestTransferPurchaseServiceImpl implements InvestTransferPurchaseService {

    static Logger logger = Logger.getLogger(InvestTransferPurchaseServiceImpl.class);

    private final static String REPAY_ORDER_ID_SEPARATOR = "X";

    private final static String REPAY_ORDER_ID_TEMPLATE = "{0}" + REPAY_ORDER_ID_SEPARATOR + "{1}";

    @Autowired
    private InvestMapper investMapper;

    @Autowired
    private InvestRepayMapper investRepayMapper;

    @Autowired
    private AccountMapper accountMapper;

    @Autowired
    private InvestExtraRateMapper investExtraRateMapper;

    @Autowired
    private PayAsyncClient payAsyncClient;

    @Autowired
    private PaySyncClient paySyncClient;

    @Autowired
    private MQWrapperClient mqWrapperClient;

    @Autowired
    private TransferApplicationMapper transferApplicationMapper;

    @Autowired
    private InvestTransferNotifyRequestMapper investTransferNotifyRequestMapper;

    @Value("${common.environment}")
    private Environment environment;

    @Autowired
    private CouponRepayMapper couponRepayMapper;

    @Autowired
    private MembershipPrivilegePurchaseService membershipPrivilegePurchaseService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private LoanMapper loanMapper;

    @Value("${pay.overdue.fee}")
    private double overdueFee;

    @Override
    public BaseDto<PayDataDto> noPasswordPurchase(InvestDto investDto) {
        BaseDto<PayDataDto> baseDto = new BaseDto<>();
        PayDataDto payDataDto = new PayDataDto();
        baseDto.setData(payDataDto);

        String loginName = investDto.getLoginName();
        AccountModel accountModel = accountMapper.lockByLoginName(loginName);
        TransferApplicationModel transferApplicationModel = transferApplicationMapper.findById(Long.parseLong(investDto.getTransferApplicationId()));
        if (transferApplicationModel == null || transferApplicationModel.getStatus() != TransferStatus.TRANSFERRING) {
            payDataDto.setMessage("该项目已转让，请购买其他项目");
            return baseDto;
        }

        if (transferApplicationModel.getTransferAmount() > accountModel.getBalance()) {
            payDataDto.setMessage("余额不足，请充值");
            return baseDto;
        }

        InvestModel transferrerModel = investMapper.findById(transferApplicationModel.getTransferInvestId());
        double rate = membershipPrivilegePurchaseService.obtainServiceFee(loginName);
        InvestModel investModel = generateInvestModel(investDto, loginName, transferApplicationModel, transferrerModel, rate);

        investMapper.create(investModel);

        logger.info(MessageFormat.format("[Transfer No Password Invest Request Data] user={0}, loan={1}, invest={2}, transferInvest={3}, amount={4}, source={5}",
                investDto.getLoginName(),
                investDto.getLoanId(),
                String.valueOf(investModel.getId()),
                String.valueOf(investModel.getTransferInvestId()),
                investDto.getAmount(),
                investDto.getSource()));

        ProjectTransferNopwdRequestModel requestModel = ProjectTransferNopwdRequestModel.newPurchaseNopwdRequest(String.valueOf(investModel.getLoanId()),
                String.valueOf(investModel.getId()),
                accountModel.getPayUserId(),
                String.valueOf(transferApplicationModel.getTransferAmount()));

        try {
            ProjectTransferNopwdResponseModel responseModel = paySyncClient.send(
                    ProjectTransferNopwdMapper.class,
                    requestModel,
                    ProjectTransferNopwdResponseModel.class);
            payDataDto.setStatus(responseModel.isSuccess());
            payDataDto.setCode(responseModel.getRetCode());
            payDataDto.setExtraValues(Maps.newHashMap(ImmutableMap.<String, String>builder()
                    .put("order_id", String.valueOf(investModel.getId()))
                    .build()));
            payDataDto.setMessage(responseModel.getRetMsg());
        } catch (PayException e) {
            investModel.setStatus(InvestStatus.FAIL);
            investMapper.update(investModel);
            payDataDto.setStatus(false);
            payDataDto.setMessage(e.getLocalizedMessage());
            logger.error(e.getLocalizedMessage(), e);
        }
        return baseDto;
    }

    @Override
    @Transactional
    public BaseDto<PayFormDataDto> purchase(InvestDto investDto) {
        //TODO: verify transfer

        String transferee = investDto.getLoginName();
        AccountModel transfereeAccount = accountMapper.findByLoginName(transferee);

        BaseDto<PayFormDataDto> dto = new BaseDto<>();
        PayFormDataDto payFormDataDto = new PayFormDataDto();
        dto.setData(payFormDataDto);
        TransferApplicationModel transferApplicationModel = transferApplicationMapper.findById(Long.parseLong(investDto.getTransferApplicationId()));

        if (transferApplicationModel == null || transferApplicationModel.getStatus() != TransferStatus.TRANSFERRING) {
            payFormDataDto.setMessage("该项目已转让，请购买其他项目");
            return dto;
        }

        if (transferApplicationModel.getTransferAmount() > transfereeAccount.getBalance()) {
            payFormDataDto.setMessage("余额不足，请充值");
            return dto;
        }

        InvestModel transferrerModel = investMapper.findById(transferApplicationModel.getTransferInvestId());
        double rate = membershipPrivilegePurchaseService.obtainServiceFee(transferee);
        InvestModel investModel = generateInvestModel(investDto, transferee, transferApplicationModel, transferrerModel, rate);

        investMapper.create(investModel);

        logger.info(MessageFormat.format("[Transfer Invest Request Data] user={0}, loan={1}, invest={2}, transferInvest={3}, amount={4}, source={5}",
                investDto.getLoginName(),
                investDto.getLoanId(),
                String.valueOf(investModel.getId()),
                String.valueOf(investModel.getTransferInvestId()),
                investDto.getAmount(),
                investDto.getSource()));

        try {
            ProjectTransferRequestModel requestModel = ProjectTransferRequestModel.newInvestTransferRequest(
                    String.valueOf(investModel.getLoanId()),
                    String.valueOf(investModel.getId()),
                    transfereeAccount.getPayUserId(),
                    String.valueOf(transferApplicationModel.getTransferAmount()),
                    investDto.getSource());
            return payAsyncClient.generateFormData(ProjectTransferMapper.class, requestModel);
        } catch (PayException e) {
            logger.error(MessageFormat.format("{0} purchase transfer(transferApplicationId={1}) is failed", transferee, String.valueOf(transferApplicationModel.getId())), e);
        }

        return dto;
    }

    @Override
    public String purchaseCallback(Map<String, String> paramsMap, String originalQueryString) {
        BaseCallbackRequestModel callbackRequest = this.payAsyncClient.parseCallbackRequest(
                paramsMap,
                originalQueryString,
                InvestTransferNotifyRequestMapper.class,
                InvestNotifyRequestModel.class);

        if (callbackRequest == null) {
            return null;
        }

        mqWrapperClient.sendMessage(MessageQueue.TransferInvestCallback, String.valueOf(callbackRequest.getId()));
        return callbackRequest.getResponseData();
    }

    @Override
    public BaseDto<PayDataDto> asyncPurchaseCallback(long notifyRequestId) {
        InvestNotifyRequestModel model = investTransferNotifyRequestMapper.findById(notifyRequestId);
        PayDataDto baseDataDto = new PayDataDto();
        baseDataDto.setStatus(true);

        if (model == null) {
            logger.error(MessageFormat.format("债权转让出借回调处理错误。{0},{1} 发送请求记录不存在", environment, String.valueOf(notifyRequestId)));
            sendFatalNotify(MessageFormat.format("债权转让出借回调处理错误。{0},{1} 发送请求记录不存在", environment, String.valueOf(notifyRequestId)));
            return new BaseDto<>(baseDataDto);
        }

        if (NotifyProcessStatus.NOT_DONE.name().equals(model.getStatus())) {
            logger.info(MessageFormat.format("[Invest Transfer Callback {0}] starting...", model.getOrderId()));
            if (updateInvestTransferNotifyRequestStatus(model)) {
                try {
                    processOneCallback(model);
                } catch (Exception e) {
                    String errMsg = MessageFormat.format("invest callback, processOneCallback error. investId:{0}", model.getOrderId());
                    logger.error(errMsg, e);
                    sendFatalNotify(MessageFormat.format("债权转让出借回调处理错误。{0},{1}", environment, errMsg));
                }
            }
        }

        return new BaseDto<>(baseDataDto);
    }

    /**
     * umpay 债权转让超投返款的回调
     *
     * @param paramsMap
     * @param queryString
     * @return
     */
    @Override
    public String overInvestTransferPaybackCallback(Map<String, String> paramsMap, String queryString) {

        logger.info("into over_invest_transfer_payback_callback, queryString: " + queryString);

        BaseCallbackRequestModel callbackRequest = this.payAsyncClient.parseCallbackRequest(
                paramsMap,
                queryString,
                ProjectTransferNotifyMapper.class,
                ProjectTransferNotifyRequestModel.class);

        if (callbackRequest == null) {
            return null;
        }

        String orderIdOri = callbackRequest.getOrderId();
        String orderIdStr = orderIdOri == null ? "" : orderIdOri.split("X")[0];
        long orderId = Long.parseLong(orderIdStr);

        InvestModel investModel = investMapper.findById(orderId);
        if (investModel == null) {
            logger.error(MessageFormat.format("invest callback notify order is not exist (orderId = {0})", orderId));
            return null;
        }

        String loginName = investModel.getLoginName();
        if (callbackRequest.isSuccess()) {
            // 返款成功
            // 改 invest 本身状态为超投返款
            investModel.setStatus(InvestStatus.OVER_INVEST_PAYBACK);
            investModel.setTradingTime(new Date());
            investMapper.update(investModel);
        } else {
            // 返款失败，发报警短信，手动干预
            String errMsg = MessageFormat.format("invest transfer pay back notify fail. orderId:{0}, amount:{1}, loginName:{2}, loanId:{3}.", orderIdStr, investModel.getAmount(), loginName, investModel.getLoanId());
            logger.error(errMsg);
            sendFatalNotify(errMsg);
        }

        return callbackRequest.getResponseData();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void postPurchase(long investId) {
        InvestModel investModel = investMapper.findById(investId);
        InvestModel transferInvestModel = investMapper.findById(investModel.getTransferInvestId());
        List<TransferApplicationModel> transferApplicationModels = transferApplicationMapper.findByTransferInvestId(investModel.getTransferInvestId(), Lists.newArrayList(TransferStatus.TRANSFERRING));
        TransferApplicationModel transferApplicationModel = transferApplicationModels.get(0);

        // update transferee invest status
        investModel.setStatus(InvestStatus.SUCCESS);
        // update trading time
        investModel.setTradingTime(new Date());

        investMapper.update(investModel);
        logger.info(MessageFormat.format("[Invest Transfer Callback {0}] update invest status to SUCCESS", String.valueOf(investId)));

        // generate transferee balance
        AmountTransferMessage atm = new AmountTransferMessage(TransferType.TRANSFER_OUT_BALANCE, investModel.getLoginName(), investId, transferApplicationModel.getTransferAmount(), UserBillBusinessType.INVEST_TRANSFER_IN, null, null);
        mqWrapperClient.sendMessage(MessageQueue.AmountTransfer, atm);

        logger.info(MessageFormat.format("[Invest Transfer Callback {0}] update transferee balance and user bill", String.valueOf(investId)));

        // update transferrer invest transfer status
        investMapper.updateTransferStatus(transferInvestModel.getId(), TransferStatus.SUCCESS);
        logger.info(MessageFormat.format("[Invest Transfer Callback {0}] update transferrer invest transfer status to SUCCESS", String.valueOf(investId)));

        // update extra invest rate
        InvestExtraRateModel investExtraRateModel = investExtraRateMapper.findByInvestId(investModel.getTransferInvestId());
        if (investExtraRateModel != null) {
            investExtraRateModel.setTransfer(true);
            investExtraRateMapper.update(investExtraRateModel);
        }

        // update transfer application
        transferApplicationModel.setInvestId(investModel.getId());
        transferApplicationModel.setStatus(TransferStatus.SUCCESS);
        transferApplicationModel.setTransferTime(investModel.getCreatedTime());
        transferApplicationMapper.update(transferApplicationModel);
        logger.info(MessageFormat.format("[Invest Transfer Callback {0}] update transfer application", String.valueOf(investId)));

        try {
            this.updateInvestRepay(transferApplicationModel);
            this.updateCouponRepay(transferApplicationModel.getTransferInvestId(), transferApplicationModel.getPeriod());
        } catch (Exception e) {
            logger.error(MessageFormat.format("[Invest Transfer Callback {0}] update invest repay failed", String.valueOf(investModel.getId())), e);
            this.sendFatalNotify(MessageFormat.format("债权转让({0})更新回款计划失败", String.valueOf(investId)));
        }

        this.transferPayback(transferInvestModel, transferApplicationModel);

        this.transferFee(transferInvestModel, transferApplicationModel);

        this.sendMessage(transferApplicationModel);
        this.sendLoanerMessage(investModel, transferApplicationModel);
    }


    private void transferFee(InvestModel transferInvestModel, TransferApplicationModel transferApplicationModel) {
        long transferApplicationId = transferApplicationModel.getId();
        // transfer fee
        long transferFee = transferApplicationModel.getTransferFee();

        long interestFee = transferApplicationModel.getInterestFee();

        try {
            ProjectTransferRequestModel feeRequestModel = ProjectTransferRequestModel.newRepayTransferFeeRequest(String.valueOf(transferInvestModel.getLoanId()),
                    MessageFormat.format(REPAY_ORDER_ID_TEMPLATE, String.valueOf(transferApplicationId), String.valueOf(new Date().getTime())),
                    String.valueOf(transferFee + interestFee));

            ProjectTransferResponseModel feeResponseModel = this.paySyncClient.send(ProjectTransferMapper.class, feeRequestModel, ProjectTransferResponseModel.class);
            if (feeResponseModel.isSuccess()) {

                SystemBillMessage sbm = new SystemBillMessage(SystemBillMessageType.TRANSFER_IN,
                        transferApplicationId, transferFee + interestFee, SystemBillBusinessType.TRANSFER_FEE,
                        MessageFormat.format(SystemBillDetailTemplate.TRANSFER_FEE_DETAIL_TEMPLATE.getTemplate(), transferInvestModel.getLoginName(), String.valueOf(transferApplicationId), String.valueOf(transferFee + interestFee)));
                mqWrapperClient.sendMessage(MessageQueue.SystemBill, sbm);

                logger.info(MessageFormat.format("[Invest Transfer Callback {0}] transfer fee is success", String.valueOf(transferApplicationModel.getInvestId())));
            }
        } catch (PayException e) {
            logger.error(MessageFormat.format("[Invest Transfer Callback {0}] transfer fee is failed", String.valueOf(transferApplicationModel.getInvestId())), e);
            //sms notify
            this.sendFatalNotify(MessageFormat.format("债权转让({0})服务费收取失败", String.valueOf(transferApplicationModel.getInvestId())));
        }
    }

    private void transferPayback(InvestModel transferInvestModel, TransferApplicationModel transferApplicationModel) {
        long transferApplicationId = transferApplicationModel.getId();
        // transfer fee
        long transferFee = transferApplicationModel.getTransferFee();

        long interestFee = transferApplicationModel.getInterestFee();

        // transferrer payback amount
        long paybackAmount = transferApplicationModel.getTransferAmount() - transferFee - interestFee;

        try {
            ProjectTransferRequestModel paybackRequestModel = ProjectTransferRequestModel.newInvestTransferPaybackRequest(String.valueOf(transferInvestModel.getLoanId()),
                    MessageFormat.format(REPAY_ORDER_ID_TEMPLATE, String.valueOf(transferApplicationId), String.valueOf(new Date().getTime())),
                    accountMapper.findByLoginName(transferInvestModel.getLoginName()).getPayUserId(),
                    String.valueOf(paybackAmount));

            ProjectTransferResponseModel paybackResponseModel = this.paySyncClient.send(ProjectTransferMapper.class, paybackRequestModel, ProjectTransferResponseModel.class);
            if (paybackResponseModel.isSuccess()) {
                AmountTransferMessage transferAtm = new AmountTransferMessage(TransferType.TRANSFER_IN_BALANCE, transferInvestModel.getLoginName(), transferApplicationId, transferApplicationModel.getTransferAmount(), UserBillBusinessType.INVEST_TRANSFER_OUT, null, null);
                AmountTransferMessage transferFeeAtm = new AmountTransferMessage(TransferType.TRANSFER_OUT_BALANCE, transferInvestModel.getLoginName(), transferApplicationId, transferFee, UserBillBusinessType.TRANSFER_FEE, null, null);
                AmountTransferMessage transferInterestAtm = interestFee > 0 ? new AmountTransferMessage(TransferType.TRANSFER_OUT_BALANCE, transferInvestModel.getLoginName(), transferApplicationId, interestFee, UserBillBusinessType.TRANSFER_INVEST_FEE, null, null) : null;
                transferFeeAtm.setNext(transferInterestAtm);
                transferAtm.setNext(transferFeeAtm);
                mqWrapperClient.sendMessage(MessageQueue.AmountTransfer, transferAtm);
                logger.info(MessageFormat.format("[Invest Transfer Callback {0}] transfer payback transferrer is success", String.valueOf(transferApplicationModel.getInvestId())));
            }
        } catch (PayException e) {
            logger.error(MessageFormat.format("[Invest Transfer Callback {0}] transfer payback transferrer is failed", String.valueOf(transferApplicationModel.getInvestId())), e);
            //sms notify
            this.sendFatalNotify(MessageFormat.format("债权转让(0)返款转让人失败", String.valueOf(transferApplicationModel.getInvestId())));
        }
    }

    private void updateCouponRepay(long investId, final int period) {
        List<CouponRepayModel> transferredCouponRepayModels = Lists.newArrayList(Iterables.filter(couponRepayMapper.findByUserCouponByInvestId(investId), new Predicate<CouponRepayModel>() {
            @Override
            public boolean apply(CouponRepayModel input) {
                return input.getPeriod() >= period;
            }
        }));

        for (CouponRepayModel couponRepayModel : transferredCouponRepayModels) {
            couponRepayModel.setExpectedInterest(0);
            couponRepayModel.setExpectedFee(0);
            couponRepayModel.setTransferred(true);
            couponRepayModel.setStatus(RepayStatus.COMPLETE);
            couponRepayMapper.update(couponRepayModel);
        }
    }

    private void updateInvestRepay(TransferApplicationModel transferApplicationModel) {
        long transferInvestId = transferApplicationModel.getTransferInvestId();
        long investId = transferApplicationModel.getInvestId();
        InvestModel investModel = investMapper.findById(investId);
        final int transferBeginWithPeriod = transferApplicationModel.getPeriod();

        List<InvestRepayModel> transferrerTransferredInvestRepayModels = Lists.newArrayList(Iterables.filter(investRepayMapper.findByInvestIdAndPeriodAsc(transferInvestId), new Predicate<InvestRepayModel>() {
            @Override
            public boolean apply(InvestRepayModel input) {
                return input.getPeriod() >= transferBeginWithPeriod;
            }
        }));

        List<InvestRepayModel> transfereeInvestRepayModels = Lists.newArrayList();
        //
        Collections.sort(transferrerTransferredInvestRepayModels, (o1, o2) -> {
            return o1.getPeriod() - o2.getPeriod();
        });
        boolean isOverdue = investMapper.findById(transferInvestId).isOverdueTransfer();
        for (int i = 0; i < transferrerTransferredInvestRepayModels.size(); i++) {
            InvestRepayModel transferrerTransferredInvestRepayModel = transferrerTransferredInvestRepayModels.get(i);
            long expectedFee = new BigDecimal(transferrerTransferredInvestRepayModel.getExpectedInterest()).setScale(0, BigDecimal.ROUND_DOWN).multiply(new BigDecimal(investModel.getInvestFeeRate())).longValue();
            InvestRepayModel transfereeInvestRepayModel = new InvestRepayModel(IdGenerator.generate(),
                    investId,
                    transferrerTransferredInvestRepayModel.getPeriod(),
                    transferrerTransferredInvestRepayModel.getCorpus(),
                    transferrerTransferredInvestRepayModel.getExpectedInterest(),
                    isOverdue ? 0: expectedFee,
                    transferrerTransferredInvestRepayModel.getRepayDate(),
                    transferrerTransferredInvestRepayModel.getStatus());
            transfereeInvestRepayModel.setOverdueInterest(transferrerTransferredInvestRepayModel.getOverdueInterest());
            transfereeInvestRepayModel.setDefaultInterest(transferrerTransferredInvestRepayModel.getDefaultInterest());
            //不是最后一期逾期不让转让，因为每天定时任务计算罚息，如果申请项目 多天没有转出来，利息属于最终承让人
            if (i == (transferrerTransferredInvestRepayModels.size() - 1) && transferrerTransferredInvestRepayModel.getStatus() == RepayStatus.OVERDUE) {
                LoanModel loanModel = loanMapper.findById(investModel.getLoanId());
                //逾期利息 手续费 需要重新计算
                long overdueInterest = InterestCalculator.calculateLoanInterest(loanModel.getBaseRate(), investModel.getAmount(), new DateTime(transferApplicationModel.getApplicationTime()), new DateTime());
                long overdueFeeValue = new BigDecimal(overdueInterest).setScale(0, BigDecimal.ROUND_DOWN).multiply(new BigDecimal(investModel.getInvestFeeRate())).longValue();
                transfereeInvestRepayModel.setOverdueFee(overdueFeeValue);
                //逾期手续费需要计算到第一期中
                long investRepayDefaultInterest = InterestCalculator.calculateLoanInterestDateRate(overdueFee, investModel.getAmount(), new DateTime(transferApplicationModel.getApplicationTime()), new DateTime());
                long investRepayDefaultFee = new BigDecimal(investRepayDefaultInterest).multiply(new BigDecimal(investModel.getInvestFeeRate())).setScale(0, BigDecimal.ROUND_DOWN).longValue();
                if (i == 0) {
                    transfereeInvestRepayModel.setDefaultFee(investRepayDefaultFee);
                } else {
                    transfereeInvestRepayModels.get(0).setDefaultFee(investRepayDefaultFee);
                }
            }
            transferrerTransferredInvestRepayModel.setExpectedInterest(0);
            transferrerTransferredInvestRepayModel.setExpectedFee(0);
            transferrerTransferredInvestRepayModel.setCorpus(0);
            transferrerTransferredInvestRepayModel.setTransferred(true);
            transferrerTransferredInvestRepayModel.setStatus(RepayStatus.COMPLETE);

            investRepayMapper.update(transferrerTransferredInvestRepayModel);

            transfereeInvestRepayModels.add(transfereeInvestRepayModel);
        }

        investRepayMapper.create(transfereeInvestRepayModels);
    }

    private boolean updateInvestTransferNotifyRequestStatus(InvestNotifyRequestModel model) {
        try {
            investTransferNotifyRequestMapper.updateStatus(model.getId(), NotifyProcessStatus.DONE);
            logger.info(MessageFormat.format("[Invest Transfer Callback {0}] decrease request count and update request status to DONE", model.getOrderId()));
            return true;
        } catch (Exception e) {
            logger.error(MessageFormat.format("[Invest Transfer Callback {0}] update request status is failed", model.getOrderId()), e);
            this.sendFatalNotify(MessageFormat.format("债权转让出借({0})回调状态更新错误", model.getOrderId()));
        }

        return false;
    }

    private void processOneCallback(InvestNotifyRequestModel callbackRequestModel) throws AmountTransferException {
        long investId = Long.parseLong(callbackRequestModel.getOrderId());
        InvestModel investModel = investMapper.findById(investId);

        if (investModel == null) {
            logger.error(MessageFormat.format("[Invest Transfer Callback {0}] invest transfer callback order is not exist", callbackRequestModel.getOrderId()));
            return;
        }

        if (investModel.getStatus() != InvestStatus.WAIT_PAY) {
            logger.error(MessageFormat.format("[Invest Transfer Callback {0}] invest transfer status({1}) is not WAIT_PAY", callbackRequestModel.getOrderId(), investModel.getStatus()));
            return;
        }

        if (!callbackRequestModel.isSuccess()) {
            // 失败的话：更新 invest 状态为出借失败
            logger.info(MessageFormat.format("[Invest Transfer Callback {0}] invest transfer callback is failed", String.valueOf(investId)));
            investModel.setStatus(InvestStatus.FAIL);
            investMapper.update(investModel);
            return;
        }

        List<TransferApplicationModel> transferredTransferApplications = transferApplicationMapper.findByTransferInvestId(investModel.getTransferInvestId(), Lists.newArrayList(TransferStatus.SUCCESS));
        if (!transferredTransferApplications.isEmpty() && transferredTransferApplications.get(0).getInvestId() != investId) {
            logger.info(MessageFormat.format("[Invest Transfer Callback {0}] invest transfer is over invest", String.valueOf(investId)));
            this.overInvestPaybackProcess(transferredTransferApplications.get(0), investId);
            return;
        }

        List<TransferApplicationModel> transferringTransferApplications = transferApplicationMapper.findByTransferInvestId(investModel.getTransferInvestId(), Lists.newArrayList(TransferStatus.TRANSFERRING));
        if (CollectionUtils.isEmpty(transferringTransferApplications)) {
            logger.error(MessageFormat.format("[Invest Transfer Callback {0}] invest transfer(transferring) is not exist", String.valueOf(investId)));
            return;
        }

        TransferApplicationModel transferringTransferApplication = transferringTransferApplications.get(0);
        logger.info(MessageFormat.format("[Invest Transfer Callback {0}] invest transfer is success", String.valueOf(investId)));
        ((InvestTransferPurchaseService) AopContext.currentProxy()).postPurchase(investId);

        logger.info("债权转让：生成合同，转让ID:" + transferringTransferApplication.getId());
        mqWrapperClient.sendMessage(MessageQueue.TransferAnxinContract, new AnxinContractMessage(transferringTransferApplication.getId(), AnxinContractType.TRANSFER_CONTRACT.name()));
    }


    /**
     * 超投处理：返款、更新出借状态为失败
     *
     * @param transferApplicationModel
     * @param investId
     */
    private void overInvestPaybackProcess(TransferApplicationModel transferApplicationModel, long investId) {
        long transferAmount = transferApplicationModel.getTransferAmount();
        InvestModel investModel = investMapper.findById(investId);

        try {
            String overInvestPaybackOrderId = MessageFormat.format(REPAY_ORDER_ID_TEMPLATE, String.valueOf(investId), String.valueOf(System.currentTimeMillis()));
            AccountModel accountModel = accountMapper.findByLoginName(investModel.getLoginName());
            ProjectTransferRequestModel requestModel = ProjectTransferRequestModel.newOverInvestPaybackRequest(String.valueOf(investModel.getLoanId()),
                    overInvestPaybackOrderId,
                    accountModel.getPayUserId(),
                    String.valueOf(transferAmount));
            ProjectTransferResponseModel responseModel = paySyncClient.send(ProjectTransferMapper.class,
                    requestModel,
                    ProjectTransferResponseModel.class);

            if (responseModel.isSuccess()) {
                // 超投返款成功
                logger.info(MessageFormat.format("[Invest Transfer Callback {0}] invest transfer over invest payback({1}) is success", String.valueOf(investId), String.valueOf(transferAmount)));
                return;
            }
        } catch (Exception e) {
            // 所有其他异常，包括数据库链接，网络异常，记录日志，发短信通知管理员，抛出异常，事务回滚。
            logger.error(e.getLocalizedMessage(), e);
        }

        // 联动优势返回返款失败，但是标记此条请求已经处理完成，记录日志，在异步notify中进行出借成功处理
        logger.error(MessageFormat.format("[Invest Transfer Callback {0}] invest transfer over invest payback({1}) is failed", String.valueOf(investId), String.valueOf(transferAmount)));
        // 如果返款失败，则记录本次出借为 超投返款失败
        investModel.setStatus(InvestStatus.OVER_INVEST_PAYBACK_FAIL);
        investMapper.update(investModel);
        sendFatalNotify(MessageFormat.format("债权转让购买({0})超投返款失败", String.valueOf(investId)));
    }

    private void sendFatalNotify(String message) {
        mqWrapperClient.sendMessage(MessageQueue.SmsFatalNotify, message);
    }

    private InvestModel generateInvestModel(InvestDto investDto, String loginName, TransferApplicationModel transferApplicationModel, InvestModel transferrerModel, double rate) {
        InvestModel investModel = new InvestModel(IdGenerator.generate(),
                transferApplicationModel.getLoanId(),
                transferApplicationModel.getTransferInvestId(),
                transferrerModel.getAmount(),
                loginName,
                transferrerModel.getInvestTime(),
                investDto.getSource(),
                investDto.getChannel(),
                rate);
        investModel.setTransferStatus(TransferStatus.NONTRANSFERABLE);
        investModel.setInvestFeeRate(membershipPrivilegePurchaseService.obtainServiceFee(loginName));
        investModel.setNoPasswordInvest(investDto.isNoPassword());
        return investModel;
    }

    private void sendMessage(TransferApplicationModel transferApplicationModel) {
        //Title:您发起的转让项目转让成功，{0}元已发放至您的账户！
        //Content:尊敬的用户，您发起的转让项目{0}已经转让成功，资金已经到达您的账户，感谢您选择拓天速贷。

        String title = MessageFormat.format(MessageEventType.TRANSFER_SUCCESS.getTitleTemplate(), AmountConverter.convertCentToString(transferApplicationModel.getTransferAmount()));
        String content = MessageFormat.format(MessageEventType.TRANSFER_SUCCESS.getContentTemplate(), transferApplicationModel.getName());
        mqWrapperClient.sendMessage(MessageQueue.EventMessage, new EventMessage(MessageEventType.TRANSFER_SUCCESS,
                Lists.newArrayList(transferApplicationModel.getLoginName()),
                title,
                content,
                transferApplicationModel.getId()));

        mqWrapperClient.sendMessage(MessageQueue.PushMessage, new PushMessage(Lists.newArrayList(transferApplicationModel.getLoginName()),
                PushSource.ALL,
                PushType.TRANSFER_SUCCESS,
                title,
                AppUrl.MESSAGE_CENTER_LIST));

        mqWrapperClient.sendMessage(MessageQueue.WeChatMessageNotify, new WeChatMessageNotify(transferApplicationModel.getLoginName(), WeChatMessageType.TRANSFER_SUCCESS, transferApplicationModel.getId()));

        String mobile = userMapper.findByLoginName(transferApplicationModel.getLoginName()).getMobile();
        mqWrapperClient.sendMessage(MessageQueue.SmsNotify, new SmsNotifyDto(JianZhouSmsTemplate.SMS_TRANSFER_LOAN_SUCCESS_TEMPLATE, Lists.newArrayList(mobile), Lists.newArrayList(transferApplicationModel.getName())));
    }

    private void sendLoanerMessage(InvestModel investModel, TransferApplicationModel transferApplicationModel) {
        //转让成功，发送给借款人消息
        try {
            String title = MessageEventType.TRANSFER_SUCCESS_LOANER.getTitleTemplate();
            LoanModel loanModel = loanMapper.findById(investModel.getLoanId());
            String content = MessageFormat.format(MessageEventType.TRANSFER_SUCCESS_LOANER.getContentTemplate(), loanModel.getName(), userMapper.findByLoginName(transferApplicationModel.getLoginName()).getUserName(), AmountConverter.convertCentToString(transferApplicationModel.getInvestAmount()), userMapper.findByLoginName(investModel.getLoginName()).getUserName());
            mqWrapperClient.sendMessage(MessageQueue.EventMessage, new EventMessage(MessageEventType.TRANSFER_SUCCESS_LOANER,
                    Lists.newArrayList(loanModel.getAgentLoginName()),
                    title,
                    content,
                    transferApplicationModel.getId()));
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage(), e);
        }
    }
}
