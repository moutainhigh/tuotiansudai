package com.tuotiansudai.api.controller.v1_0;

import com.tuotiansudai.api.dto.v1_0.BaseResponseDto;
import com.tuotiansudai.api.dto.v1_0.ReferrerListRequestDto;
import com.tuotiansudai.api.dto.v1_0.ReferrerListResponseDataDto;
import com.tuotiansudai.api.service.v1_0.MobileAppReferrerListService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(description = "我的推荐人列表")
public class MobileAppReferrerListController extends MobileAppBaseController {
    @Autowired
    private MobileAppReferrerListService mobileAppReferrerListService;

    @RequestMapping(value = "/get/referrers", method = RequestMethod.POST)
    @ApiOperation("我的推荐人列表")
    public BaseResponseDto<ReferrerListResponseDataDto> queryInvestList(@RequestBody ReferrerListRequestDto referrerListRequestDto) {
        referrerListRequestDto.getBaseParam().setUserId(getLoginName());
        return mobileAppReferrerListService.generateReferrerList(referrerListRequestDto);
    }

}
