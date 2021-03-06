package com.tuotiansudai.api.controller;

import com.tuotiansudai.api.controller.v1_0.MobileAppLoanListController;
import com.tuotiansudai.api.controller.v1_0.MobileAppUserInvestRepayController;
import com.tuotiansudai.api.dto.v1_0.LoanListRequestDto;
import com.tuotiansudai.api.dto.v1_0.UserInvestRepayRequestDto;
import com.tuotiansudai.api.service.v1_0.MobileAppLoanListService;
import com.tuotiansudai.api.service.v1_0.MobileAppUserInvestRepayService;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;


public class MobileAppUserInvestRepayControllerTest extends ControllerTestBase {

    @InjectMocks
    private MobileAppUserInvestRepayController controller;
    @Mock
    private MobileAppUserInvestRepayService service;


    @Override
    protected Object getControllerObject() {
        return controller;
    }

    @Test
    public void shouldUserInvestRepayIsOk() throws Exception {
        when(service.userInvestRepay(any(UserInvestRepayRequestDto.class))).thenReturn(successResponseDto);
        doRequestWithServiceMockedTest("/get/user-invest-repay", new UserInvestRepayRequestDto());
    }

}
