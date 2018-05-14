package com.tuotiansudai.fudian.service;

import com.google.common.base.Strings;
import com.tuotiansudai.fudian.config.ApiType;
import com.tuotiansudai.fudian.dto.request.LoanInvestRequestDto;
import com.tuotiansudai.fudian.dto.request.Source;
import com.tuotiansudai.fudian.dto.response.LoanInvestContentDto;
import com.tuotiansudai.fudian.dto.response.ResponseDto;
import com.tuotiansudai.fudian.mapper.InsertMapper;
import com.tuotiansudai.fudian.mapper.SelectResponseDataMapper;
import com.tuotiansudai.fudian.mapper.UpdateMapper;
import com.tuotiansudai.fudian.sign.SignatureHelper;
import com.tuotiansudai.fudian.util.BankClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoanInvestService implements AsyncCallbackInterface {

    private static Logger logger = LoggerFactory.getLogger(LoanInvestService.class);

    private final SignatureHelper signatureHelper;

    private final InsertMapper insertMapper;

    private final UpdateMapper updateMapper;

    private final BankClient bankClient;

    private final SelectResponseDataMapper selectResponseDataMapper;

    @Autowired
    public LoanInvestService(SignatureHelper signatureHelper, BankClient bankClient, InsertMapper insertMapper, UpdateMapper updateMapper, SelectResponseDataMapper selectResponseDataMapper) {
        this.signatureHelper = signatureHelper;
        this.bankClient = bankClient;
        this.insertMapper = insertMapper;
        this.updateMapper = updateMapper;
        this.selectResponseDataMapper = selectResponseDataMapper;
    }

    public LoanInvestRequestDto invest(Source source, String loginName, String mobile, String userName, String accountNo, String amount, String award, String loanTxNo) {
        LoanInvestRequestDto dto = new LoanInvestRequestDto(source, loginName, mobile, userName, accountNo, amount, award, loanTxNo, ApiType.LOAN_INVEST);
        signatureHelper.sign(dto);

        if (Strings.isNullOrEmpty(dto.getRequestData())) {
            logger.error("[loan invest] sign error, userName: {}, accountNo: {}, amount: {}, award: {}, loanTxNo: {}",
                    userName, accountNo, amount, award, loanTxNo);
            return null;
        }

        insertMapper.insertLoanInvest(dto);
        return dto;
    }

    public ResponseDto fastInvest(Source source, String loginName, String mobile, String userName, String accountNo, String amount, String award, String loanTxNo) {
        LoanInvestRequestDto dto = new LoanInvestRequestDto(source, loginName, mobile, userName, accountNo, amount, award, loanTxNo, ApiType.LOAN_FAST_INVEST);

        signatureHelper.sign(dto);

        if (Strings.isNullOrEmpty(dto.getRequestData())) {
            logger.error("[loan fast invest] sign error, userName: {}, accountNo: {}, amount: {}, award: {}, loanTxNo: {}",
                    userName, accountNo, amount, award, loanTxNo);
            return null;
        }

        insertMapper.insertLoanInvest(dto);

        String responseData = bankClient.send(dto.getRequestData(), ApiType.LOAN_FAST_INVEST);

        if (Strings.isNullOrEmpty(responseData)) {
            logger.error("[loan fast invest] send error, userName: {}, accountNo: {}, amount: {}, award: {}, loanTxNo: {}",
                    userName, accountNo, amount, award, loanTxNo);
            return null;
        }

        if (!signatureHelper.verifySign(responseData)) {
            logger.error("[loan fast invest] verify sign error, userName: {}, accountNo: {}, amount: {}, award: {}, loanTxNo: {}",
                    userName, accountNo, amount, award, loanTxNo);
            return null;
        }

        ResponseDto responseDto = ApiType.LOAN_FAST_INVEST.getParser().parse(responseData);

        if (responseDto == null) {
            logger.error("[loan fast invest] parse response error, userName: {}, accountNo: {}, amount: {}, award: {}, loanTxNo: {}",
                    userName, accountNo, amount, award, loanTxNo);
            return null;
        }

        this.updateMapper.updateLoanInvest(responseDto);
        return responseDto;
    }

    @Override
    public ResponseDto callback(String responseData) {
        logger.info("[loan invest callback] data is {}", responseData);

        ResponseDto responseDto = ApiType.LOAN_INVEST.getParser().parse(responseData);

        if (responseDto == null) {
            logger.error("[loan invest callback] parse callback data error, data is {}", responseData);
            return null;
        }

        responseDto.setReqData(responseData);
        updateMapper.updateLoanInvest(responseDto);

        return responseDto;
    }

    @Override
    @SuppressWarnings(value = "unchecked")
    public Boolean isSuccess(String orderNo) {
        String responseData = this.selectResponseDataMapper.selectResponseData(ApiType.LOAN_INVEST.name().toLowerCase(), orderNo);
        if (Strings.isNullOrEmpty(responseData)) {
            return null;
        }

        ResponseDto<LoanInvestContentDto> responseDto = (ResponseDto<LoanInvestContentDto>) ApiType.LOAN_INVEST.getParser().parse(responseData);

        return responseDto.isSuccess();
    }
}
