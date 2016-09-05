package com.tuotiansudai.api.controller.v3_0;

import com.tuotiansudai.api.dto.v1_0.BaseResponseDto;
import com.tuotiansudai.api.dto.v1_0.UserInvestRepayRequestDto;
import com.tuotiansudai.api.service.v3_0.MobileAppUserInvestRepayV3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class MobileAppTransferApplicationV3Controller extends MobileAppBaseController {
    @Autowired
    private MobileAppUserInvestRepayV3Service mobileAppUserInvestRepayV3Service;

    @RequestMapping(value = "/get/user-transfer-repay", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponseDto getTransferLoanRepay(@RequestBody UserInvestRepayRequestDto userInvestRepayRequestDto) {
        return mobileAppUserInvestRepayV3Service.userInvestRepay(userInvestRepayRequestDto);
    }
}
