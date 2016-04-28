package com.tuotiansudai.api.dto;


import org.hibernate.validator.constraints.NotEmpty;

public class ExchangeRequestDto extends BaseParamDto{
    @NotEmpty(message = "0023")
    private String exchangeCode;

    public String getExchangeCode() {
        return exchangeCode;
    }

    public void setExchangeCode(String exchangeCode) {
        this.exchangeCode = exchangeCode;
    }
}
