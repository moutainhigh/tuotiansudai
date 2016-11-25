package com.tuotiansudai.api.service;

import com.tuotiansudai.api.dto.v1_0.BaseParam;
import com.tuotiansudai.api.dto.v1_0.BaseResponseDto;
import com.tuotiansudai.api.dto.v1_0.NoPasswordInvestTurnOnRequestDto;
import com.tuotiansudai.api.service.v1_0.impl.MobileAppNoPasswordInvestTurnOnServiceImpl;
import com.tuotiansudai.repository.mapper.AccountMapper;
import com.tuotiansudai.repository.model.AccountModel;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class MobileAppNoPasswordInvestServiceTurnOnTest extends ServiceTestBase {
    @InjectMocks
    private MobileAppNoPasswordInvestTurnOnServiceImpl mobileAppNoPasswordInvestTurnOnService;

    @Mock
    private AccountMapper accountMapper;

    @Ignore
    public void shouldNoPasswordInvestTurnOnIsOk() {
        AccountModel accountModel = new AccountModel("loginName", "payUserId", "payAccountId", new Date());
        accountModel.setNoPasswordInvest(true);
        when(accountMapper.findByLoginName(anyString())).thenReturn(accountModel);
        NoPasswordInvestTurnOnRequestDto noPasswordInvestTurnOnRequestDto = new NoPasswordInvestTurnOnRequestDto();
        BaseParam baseParam = new BaseParam();
        baseParam.setUserId("loginName");
        noPasswordInvestTurnOnRequestDto.setBaseParam(baseParam);
        BaseResponseDto baseResponseDto = mobileAppNoPasswordInvestTurnOnService.noPasswordInvestTurnOn(noPasswordInvestTurnOnRequestDto, "127.0.0.1");

        assertEquals("0000", baseResponseDto.getCode());
        assertEquals("", baseResponseDto.getMessage());

    }

}
