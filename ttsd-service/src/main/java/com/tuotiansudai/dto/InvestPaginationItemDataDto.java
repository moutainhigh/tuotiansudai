package com.tuotiansudai.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.common.collect.Lists;
import com.tuotiansudai.repository.model.InvestPaginationItemView;
import com.tuotiansudai.repository.model.InvestStatus;
import com.tuotiansudai.repository.model.LoanStatus;
import com.tuotiansudai.repository.model.Role;
import com.tuotiansudai.util.AmountConverter;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.Date;

public class InvestPaginationItemDataDto implements Serializable {

    private long investId;

    private long loanId;

    private String loanName;

    private String loanType;

    private int loanPeriods;

    private String amount;

    private String investorLoginName;

    private String investorRealName;

    private String investorMobile;

    private String referrerLoginName;

    private String referrerRealName;

    private String referrerMobile;

    private String source;

    private String channel;

    private String roles;

    private boolean isAutoInvest;

    private String status;

    private Date createdTime;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date nextRepayDate;

    private String nextRepayAmount;

    private boolean hasInvestRepay;

    public InvestPaginationItemDataDto(InvestPaginationItemView view) {
        this.investId = view.getId();
        this.loanId = view.getLoanId();
        this.amount = AmountConverter.convertCentToString(view.getAmount());
        this.loanName = view.getLoanName();
        this.investorLoginName = view.getLoginName();
        this.investorRealName = view.getInvestorRealName();
        this.investorMobile = view.getInvestorMobile();
        this.referrerLoginName = view.getReferrerLoginName();
        this.referrerRealName = view.getReferrerRealName();
        this.referrerMobile = view.getReferrerMobile();
        this.source = view.getSource().name();
        this.channel = view.getChannel();
        this.roles = view.getRoles();
        this.isAutoInvest = view.isAutoInvest();
        this.loanType = view.getLoanType().getName();
        this.loanPeriods = view.getLoanPeriods();
        this.createdTime = view.getCreatedTime();
        this.status = view.getStatus().getDescription();
        this.nextRepayDate = view.getNextRepayDate();
        this.nextRepayAmount = AmountConverter.convertCentToString(view.getNextRepayAmount());
        this.hasInvestRepay = view.getStatus() == InvestStatus.SUCCESS && Lists.newArrayList(LoanStatus.REPAYING, LoanStatus.OVERDUE, LoanStatus.COMPLETE).contains(view.getLoanStatus());
    }

    public boolean isStaff() {
        return StringUtils.containsIgnoreCase(this.roles, Role.STAFF.name());
    }

    public long getInvestId() {
        return investId;
    }

    public long getLoanId() {
        return loanId;
    }

    public String getLoanName() {
        return loanName;
    }

    public String getLoanType() {
        return loanType;
    }

    public String getAmount() {
        return amount;
    }

    public String getInvestorLoginName() {
        return investorLoginName;
    }

    public String getReferrerLoginName() {
        return referrerLoginName;
    }

    public String getSource() {
        return source;
    }

    public boolean isAutoInvest() {
        return isAutoInvest;
    }

    public String getStatus() {
        return status;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public Date getNextRepayDate() {
        return nextRepayDate;
    }

    public String getNextRepayAmount() {
        return nextRepayAmount;
    }

    public boolean isHasInvestRepay() {
        return hasInvestRepay;
    }

    public int getLoanPeriods() {
        return loanPeriods;
    }

    public void setLoanPeriods(int loanPeriods) {
        this.loanPeriods = loanPeriods;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public String getInvestorRealName() {
        return investorRealName;
    }

    public void setInvestorRealName(String investorRealName) {
        this.investorRealName = investorRealName;
    }

    public String getInvestorMobile() {
        return investorMobile;
    }

    public void setInvestorMobile(String investorMobile) {
        this.investorMobile = investorMobile;
    }

    public String getReferrerRealName() {
        return referrerRealName;
    }

    public void setReferrerRealName(String referrerRealName) {
        this.referrerRealName = referrerRealName;
    }

    public String getReferrerMobile() {
        return referrerMobile;
    }

    public void setReferrerMobile(String referrerMobile) {
        this.referrerMobile = referrerMobile;
    }
}
