package com.tuotiansudai.fudian.service;

import com.google.common.base.Strings;
import com.tuotiansudai.fudian.config.ApiType;
import com.tuotiansudai.fudian.dto.request.QueryLoanRequestDto;
import com.tuotiansudai.fudian.dto.response.QueryLoanContentDto;
import com.tuotiansudai.fudian.dto.response.ResponseDto;
import com.tuotiansudai.fudian.message.BankAsyncMessage;
import com.tuotiansudai.fudian.sign.SignatureHelper;
import com.tuotiansudai.fudian.util.BankClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QueryLoanService {

    private static Logger logger = LoggerFactory.getLogger(QueryLoanService.class);

    private final SignatureHelper signatureHelper;

    private final BankClient bankClient;

    @Autowired
    public QueryLoanService(SignatureHelper signatureHelper, BankClient bankClient) {
        this.signatureHelper = signatureHelper;
        this.bankClient = bankClient;
    }

    @SuppressWarnings(value = "unchecked")
    public ResponseDto<QueryLoanContentDto> query(String loanTxNo) {
        QueryLoanRequestDto dto = new QueryLoanRequestDto(loanTxNo);

        signatureHelper.sign(dto);
        if (Strings.isNullOrEmpty(dto.getRequestData())) {
            logger.warn("[query loan] sign error, loanTxNo: {}", loanTxNo);
            return null;
        }

        String responseData = bankClient.send(dto.getRequestData(), ApiType.QUERY_LOAN);

        if (Strings.isNullOrEmpty(responseData)) {
            logger.warn("[query loan] send error, loanTxNo: {}", loanTxNo);
            return null;
        }

        if (!signatureHelper.verifySign(responseData)) {
            logger.warn("[query loan] verify sign error, loanTxNo: {}", loanTxNo);
            return null;
        }

        ResponseDto<QueryLoanContentDto> responseDto = (ResponseDto<QueryLoanContentDto>) ApiType.QUERY_LOAN.getParser().parse(responseData);
        if (responseDto == null) {
            logger.warn("[query loan] parse response error, loanTxNo: {}", loanTxNo);
            return null;
        }

        return responseDto;
    }
}
