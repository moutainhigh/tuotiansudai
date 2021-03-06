package com.tuotiansudai.api.service;

import com.tuotiansudai.api.dto.*;
import com.tuotiansudai.api.dto.v1_0.AgreementOperateRequestDto;
import com.tuotiansudai.api.dto.v1_0.AgreementOperateResponseDataDto;
import com.tuotiansudai.api.dto.v1_0.BaseResponseDto;
import com.tuotiansudai.api.service.v1_0.impl.MobileAppAgreementServiceImpl;
import com.tuotiansudai.client.PayWrapperClient;
import com.tuotiansudai.dto.AgreementBusinessType;
import com.tuotiansudai.dto.AgreementDto;
import com.tuotiansudai.dto.BaseDto;
import com.tuotiansudai.dto.PayFormDataDto;
import com.tuotiansudai.repository.mapper.AccountMapper;
import com.tuotiansudai.repository.model.AccountModel;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

public class MobileAppAgreementServiceTest extends ServiceTestBase{

    @InjectMocks
    private MobileAppAgreementServiceImpl mobileAppAgreementService;

    @Mock
    private PayWrapperClient payWrapperClient;

    @Mock
    private AccountMapper accountMapper;

    @Test
    public void shouldGenerateAgreementRequestIsOk() {
        AgreementOperateRequestDto agreementOperateRequestDto = new AgreementOperateRequestDto();
        agreementOperateRequestDto.setType(AgreementBusinessType.AUTO_INVEST);
        agreementOperateRequestDto.setBaseParam(BaseParamTest.getInstance());

        BaseDto<PayFormDataDto> formDto = new BaseDto<>();
        PayFormDataDto dataDto = new PayFormDataDto();
        dataDto.setUrl("url");
        dataDto.setStatus(true);
        formDto.setData(dataDto);

        AccountModel accountModel = new AccountModel("testuser", "payUserId", "payAccountId", new Date());

        when(payWrapperClient.agreement(any(AgreementDto.class))).thenReturn(formDto);
        when(accountMapper.findByLoginName(anyString())).thenReturn(accountModel);
        BaseResponseDto<AgreementOperateResponseDataDto> baseResponseDto = mobileAppAgreementService.generateAgreementRequest(agreementOperateRequestDto);

        assertTrue(baseResponseDto.isSuccess());
        assertEquals("url", baseResponseDto.getData().getUrl());
    }

}
