package com.tuotiansudai.fudian.dto.request;

import com.tuotiansudai.fudian.dto.BankLoanCreditInvestDto;
import com.tuotiansudai.fudian.util.AmountUtils;

public class LoanCreditInvestRequestDto extends NotifyRequestDto {

    private String creditNo; //债权挂牌ID

    private String creditNoAmount; //债权挂牌金额

    private String loanTxNo; //标的号

    private String creditAmount; //认购本金

    private String amount; //承接金额

    private String creditFee; //转让手续费

    private String creditFeeType = "1"; //1:转让人出2：承接人出

    private String investOrderNo; //原投资记录的订单号

    private String investOrderDate; //原投资记录的订单日期

    private String oriOrderNo; //最原始投资记录订单号

    private String oriOrderDate; //最原始投资记录订单日期

    private String repayedAmount = "0.00"; //已还款金额

    public LoanCreditInvestRequestDto(Source source, BankLoanCreditInvestDto dto) {
        super(source, dto.getLoginName(), dto.getMobile(), dto.getBankUserName(), dto.getBankAccountNo());
        this.loanTxNo = dto.getLoanTxNo();
        this.investOrderNo = dto.getInvestOrderNo();
        this.oriOrderNo = dto.getInvestOrderNo();
        this.investOrderDate = dto.getInvestOrderDate();
        this.oriOrderDate = dto.getInvestOrderDate();
        this.creditNo = String.valueOf(dto.getTransferApplicationId());
        this.creditAmount = AmountUtils.toYuan(dto.getTransferAmount());
        this.creditNoAmount = AmountUtils.toYuan(dto.getInvestAmount());
        this.amount = AmountUtils.toYuan(dto.getTransferAmount());
        this.creditFee = AmountUtils.toYuan(dto.getTransferFee());
    }

    public String getCreditNo() {
        return creditNo;
    }

    public void setCreditNo(String creditNo) {
        this.creditNo = creditNo;
    }

    public String getCreditNoAmount() {
        return creditNoAmount;
    }

    public void setCreditNoAmount(String creditNoAmount) {
        this.creditNoAmount = creditNoAmount;
    }

    public String getLoanTxNo() {
        return loanTxNo;
    }

    public void setLoanTxNo(String loanTxNo) {
        this.loanTxNo = loanTxNo;
    }

    public String getCreditAmount() {
        return creditAmount;
    }

    public void setCreditAmount(String creditAmount) {
        this.creditAmount = creditAmount;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCreditFee() {
        return creditFee;
    }

    public void setCreditFee(String creditFee) {
        this.creditFee = creditFee;
    }

    public String getCreditFeeType() {
        return creditFeeType;
    }

    public void setCreditFeeType(String creditFeeType) {
        this.creditFeeType = creditFeeType;
    }

    public String getInvestOrderNo() {
        return investOrderNo;
    }

    public void setInvestOrderNo(String investOrderNo) {
        this.investOrderNo = investOrderNo;
    }

    public String getInvestOrderDate() {
        return investOrderDate;
    }

    public void setInvestOrderDate(String investOrderDate) {
        this.investOrderDate = investOrderDate;
    }

    public String getOriOrderNo() {
        return oriOrderNo;
    }

    public void setOriOrderNo(String oriOrderNo) {
        this.oriOrderNo = oriOrderNo;
    }

    public String getOriOrderDate() {
        return oriOrderDate;
    }

    public void setOriOrderDate(String oriOrderDate) {
        this.oriOrderDate = oriOrderDate;
    }

    public String getRepayedAmount() {
        return repayedAmount;
    }

    public void setRepayedAmount(String repayedAmount) {
        this.repayedAmount = repayedAmount;
    }
}