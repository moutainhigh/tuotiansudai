package com.tuotiansudai.api.controller.v1_0;

import com.tuotiansudai.api.dto.v1_0.BaseResponseDto;
import com.tuotiansudai.api.dto.v1_0.FundManagementRequestDto;
import com.tuotiansudai.api.dto.v1_0.FundManagementResponseDataDto;
import com.tuotiansudai.api.service.v1_0.MobileAppFundManagementService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(description = "资金管理－概要")
public class MobileAppFundManagementController extends MobileAppBaseController {
    @Autowired
    private MobileAppFundManagementService mobileAppFundManagementService;

    @RequestMapping(value = "/get/userfund", method = RequestMethod.POST)
    @ApiOperation("资金管理－概要")
    public BaseResponseDto<FundManagementResponseDataDto> queryFundManagement(@RequestBody FundManagementRequestDto fundManagementRequestDto) {
        fundManagementRequestDto.getBaseParam().setUserId(getLoginName());
        fundManagementRequestDto.setUserId(getLoginName());
        return mobileAppFundManagementService.queryFundByUserId(fundManagementRequestDto.getUserId());
    }

}
