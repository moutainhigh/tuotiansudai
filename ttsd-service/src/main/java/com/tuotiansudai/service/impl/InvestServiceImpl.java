package com.tuotiansudai.service.impl;

import com.google.common.collect.Lists;
import com.tuotiansudai.client.PayWrapperClient;
import com.tuotiansudai.dto.*;
import com.tuotiansudai.repository.mapper.InvestMapper;
import com.tuotiansudai.repository.mapper.LoanMapper;
import com.tuotiansudai.repository.model.InvestDetailModel;
import com.tuotiansudai.repository.model.LoanModel;
import com.tuotiansudai.repository.model.LoanPeriodUnit;
import com.tuotiansudai.repository.model.LoanType;
import com.tuotiansudai.service.InvestService;
import com.tuotiansudai.utils.InterestCalculator;
import com.tuotiansudai.utils.LoginUserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InvestServiceImpl implements InvestService {

    @Autowired
    private PayWrapperClient payWrapperClient;

    @Autowired
    private LoanMapper loanMapper;

    @Autowired
    private InvestMapper investMapper;

    @Override
    public BaseDto<PayFormDataDto> invest(WebInvestDto webInvestDto) {
        String loginName = LoginUserInfo.getLoginName();
        InvestDto investDto = webInvestDto.toInvestDto(loginName);
        if (canInvest(investDto)) {
            return payWrapperClient.invest(investDto);
        } else {
            BaseDto<PayFormDataDto> baseDto = new BaseDto<>();
            PayFormDataDto dataDto = new PayFormDataDto();
            dataDto.setStatus(false);
            baseDto.setData(dataDto);
            return baseDto;
        }
    }

    private boolean canInvest(InvestDto investDto) {
        long loanId = investDto.getLoanIdLong();
        LoanModel loan = loanMapper.findById(loanId);
        long userInvestMinAmount = loan.getMinInvestAmount();
        long investAmount = Long.parseLong(investDto.getAmount());
        long userInvestIncreasingAmount = loan.getInvestIncreasingAmount();

        // 不满足最小投资限制
        if(investAmount < userInvestMinAmount){ return false; }

        // 不满足递增规则
        if((investAmount - userInvestMinAmount) % userInvestIncreasingAmount > 0){ return false; }

        long userInvestMaxAmount = loan.getMaxInvestAmount();
        long successInvestAmount = investMapper.sumSuccessInvestAmount(loanId);
        long loanNeedAmount = loan.getLoanAmount() - successInvestAmount;

        // 标已满
        if(loanNeedAmount <= 0){ return false; }

        // 超投
        if(investAmount < loanNeedAmount){ return false; }

        long userInvestAmount = investMapper.sumSuccessInvestAmountByLoginName(loanId, investDto.getLoginName());

        // 不满足单用户投资限额
        if(investAmount > userInvestMaxAmount - userInvestAmount){ return false; }

        return true;
    }

    @Override
    public long calculateExpectedInterest(long loanId, long amount) {
        LoanModel loanModel = loanMapper.findById(loanId);
        int repayTimes = loanModel.calculateLoanRepayTimes();
        LoanType loanType = loanModel.getType();

        int daysOfMonth = 30;
        int duration = loanModel.getPeriods();
        if (loanType.getLoanPeriodUnit() == LoanPeriodUnit.MONTH) {
            duration = repayTimes * daysOfMonth;
        }
        return InterestCalculator.calculateInterest(loanModel, amount * duration);
    }

    @Override
    public BasePaginationDataDto<InvestDetailDto> queryInvests(InvestDetailQueryDto queryDto, boolean includeNextRepay) {
        int offset = (queryDto.getPageIndex() - 1) * queryDto.getPageSize();
        int limit = queryDto.getPageSize();
        List<InvestDetailModel> investModelList = investMapper.findByPage(
                queryDto.getLoanId(),
                queryDto.getLoginName(),
                queryDto.getBeginTime(),
                queryDto.getEndTime(),
                queryDto.getLoanStatus(),
                queryDto.getInvestStatus(),
                includeNextRepay,
                offset,
                limit
        );
        int count = investMapper.findCount(
                queryDto.getLoanId(),
                queryDto.getLoginName(),
                queryDto.getBeginTime(),
                queryDto.getEndTime(),
                queryDto.getLoanStatus(),
                queryDto.getInvestStatus()
        );

        List<InvestDetailDto> dtoList = Lists.newArrayList();
        for (InvestDetailModel model : investModelList) {
            dtoList.add(new InvestDetailDto(model));
        }
        BasePaginationDataDto<InvestDetailDto> paginationDto = new BasePaginationDataDto<>(
                queryDto.getPageIndex(), queryDto.getPageSize(), count, dtoList
        );
        paginationDto.setStatus(true);
        return paginationDto;
    }
}
