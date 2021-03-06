package com.tuotiansudai.paywrapper.service;

import com.tuotiansudai.dto.BaseDto;
import com.tuotiansudai.dto.InvestDto;
import com.tuotiansudai.dto.PayDataDto;
import com.tuotiansudai.dto.PayFormDataDto;
import com.tuotiansudai.exception.AmountTransferException;

import java.util.Map;

public interface InvestTransferPurchaseService {

    BaseDto<PayDataDto> noPasswordPurchase(InvestDto investDto);

    BaseDto<PayFormDataDto> purchase(InvestDto investDto);

    void postPurchase(long investId) throws AmountTransferException;

    String purchaseCallback(Map<String, String> paramsMap, String queryString);

    BaseDto<PayDataDto> asyncPurchaseCallback(long notifyRequestId);

    String overInvestTransferPaybackCallback(Map<String, String> paramsMap, String queryString);

}
