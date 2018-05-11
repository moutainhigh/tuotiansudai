package com.tuotiansudai.fudian.dto.request;

import com.tuotiansudai.fudian.config.ApiType;

public class QueryLoanRequestDto extends BaseRequestDto {

    private String loanTxNo;

    public QueryLoanRequestDto(String loginName, String mobile, String loanTxNo) {
        super(Source.WEB, loginName, mobile, ApiType.QUERY_LOAN);
        this.loanTxNo = loanTxNo;
    }

    public String getLoanTxNo() {
        return loanTxNo;
    }

    public void setLoanTxNo(String loanTxNo) {
        this.loanTxNo = loanTxNo;
    }
}
