package com.tuotiansudai.console.service;

import com.google.common.collect.Lists;
import com.tuotiansudai.dto.InvestPaginationDataDto;
import com.tuotiansudai.dto.InvestPaginationItemDataDto;
import com.tuotiansudai.enums.CouponType;
import com.tuotiansudai.enums.Role;
import com.tuotiansudai.repository.mapper.*;
import com.tuotiansudai.repository.model.*;
import com.tuotiansudai.rest.client.mapper.UserMapper;
import com.tuotiansudai.util.PaginationUtil;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ConsoleInvestService {

    static Logger logger = Logger.getLogger(ConsoleInvestService.class);

    @Value(value = "${web.coupon.lock.seconds}")
    private int couponLockSeconds;

    @Autowired
    private InvestMapper investMapper;

    @Value(value = "${web.newbie.invest.limit}")
    private int newbieInvestLimit;

    @Autowired
    private UserCouponMapper userCouponMapper;

    @Autowired
    private CouponMapper couponMapper;

    @Autowired
    private CouponRepayMapper couponRepayMapper;

    @Value(value = "${pay.interest.fee}")
    private double defaultFee;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private InvestRepayMapper investRepayMapper;

    public InvestPaginationDataDto getInvestPagination(Long loanId, String investorMobile, String channel, Source source,
                                                       Role role, Date startTime, Date endTime, InvestStatus investStatus,
                                                       PreferenceType preferenceType, ProductType productType, String transferType, int index, int pageSize) {
        UserModel investor = userMapper.findByLoginNameOrMobile(investorMobile);
        String investorLoginName = investor != null ? investor.getLoginName() : null;

        endTime = new DateTime(endTime).plusDays(1).withTimeAtStartOfDay().plusSeconds(-1).toDate();
        long count = investMapper.findCountInvestPagination(loanId, investorLoginName, channel, source, role, startTime, endTime, investStatus, preferenceType, productType, transferType);
        long investAmountSum = investMapper.sumInvestAmountConsole(loanId, investorLoginName, channel, source, role, startTime, endTime, investStatus, preferenceType, productType, transferType);
        index = PaginationUtil.validateIndex(index, pageSize, count);
        List<InvestPaginationItemView> items = investMapper.findInvestPagination(loanId, investorLoginName, channel, source, role, startTime, endTime, investStatus, preferenceType, productType, transferType, PaginationUtil.calculateOffset(index, pageSize, count), pageSize);

        List<InvestPaginationItemDataDto> records = Lists.transform(items, view -> {
            InvestPaginationItemDataDto investPaginationItemDataDto = new InvestPaginationItemDataDto(view);
            CouponModel couponModel = couponMapper.findById(view.getCouponId());
            if (null != couponModel) {
                long couponActualInterest = 0;
                if (couponModel.getCouponType().equals(CouponType.RED_ENVELOPE)) {
                    List<UserCouponModel> userCouponModels = userCouponMapper.findUserCouponSuccessByInvestId(view.getInvestId());
                    for (UserCouponModel userCouponModel : userCouponModels) {
                        couponActualInterest += userCouponModel.getActualInterest();
                    }
                } else {
                    List<CouponRepayModel> couponRepayModels = couponRepayMapper.findByUserCouponByInvestId(view.getInvestId());
                    for (CouponRepayModel couponRepayModel : couponRepayModels) {
                        couponActualInterest += couponRepayModel.getActualInterest();
                    }
                }
                investPaginationItemDataDto.setCouponActualInterest(couponActualInterest);
                investPaginationItemDataDto.setCouponDetail(couponModel);
            }
            return investPaginationItemDataDto;
        });

        InvestPaginationDataDto dto = new InvestPaginationDataDto(index, pageSize, count, records);

        dto.setSumAmount(investAmountSum);

        dto.setStatus(true);

        return dto;
    }

    public List<String> findAllChannel() {
        return investMapper.findAllChannels();
    }

    public List<String> findAllInvestChannels() {
        return investMapper.findAllInvestChannels();
    }

    public boolean updateInvestTransferStatus(long investId){
        InvestModel investModel = investMapper.findById(investId);
        List<InvestRepayModel> investRepayModels = investRepayMapper.findByInvestIdAndPeriodAsc(investId);
        RepayStatus lastPeriodRepayStatus = investRepayModels.get(investRepayModels.size() - 1).getStatus();
        if (investModel.getTransferStatus() == TransferStatus.NONTRANSFERABLE){
            investMapper.updateTransferStatus(investId, TransferStatus.TRANSFERABLE);
        }
        if (investModel.getTransferStatus() == TransferStatus.TRANSFERABLE && lastPeriodRepayStatus == RepayStatus.OVERDUE){
            investMapper.updateIsOverdueTransfer(investId);
        }
        return true;
    }
}
