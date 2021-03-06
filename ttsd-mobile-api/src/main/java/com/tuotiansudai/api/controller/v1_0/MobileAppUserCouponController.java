package com.tuotiansudai.api.controller.v1_0;

import com.google.common.collect.Lists;
import com.tuotiansudai.api.dto.v1_0.BaseResponseDto;
import com.tuotiansudai.api.dto.v1_0.ReturnMessage;
import com.tuotiansudai.api.dto.v1_0.UserCouponListResponseDataDto;
import com.tuotiansudai.api.dto.v1_0.UserCouponRequestDto;
import com.tuotiansudai.api.service.v1_0.MobileAppUserCouponService;
import com.tuotiansudai.repository.model.UserGroup;
import com.tuotiansudai.coupon.service.CouponAssignmentService;
import com.tuotiansudai.coupon.service.UserCouponService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.text.MessageFormat;

@RestController
@Api(description = "用户优惠券")
public class MobileAppUserCouponController extends MobileAppBaseController {

    static Logger logger = Logger.getLogger(MobileAppUserCouponController.class);

    @Autowired
    private MobileAppUserCouponService mobileAppUserCouponService;

    @Autowired
    private CouponAssignmentService couponAssignmentService;

    @RequestMapping(value = "/get/userCoupons", method = RequestMethod.POST)
    @ApiOperation("我的宝藏")
    public BaseResponseDto<UserCouponListResponseDataDto> getCoupons(@RequestBody UserCouponRequestDto dto) {
        dto.getBaseParam().setUserId(getLoginName());
        return mobileAppUserCouponService.getUserCoupons(dto);
    }


    @RequestMapping(value = "/assign-coupon", method = RequestMethod.POST)
    @ApiOperation("发放优惠券")
    public BaseResponseDto assignUserCoupon() {
        String loginName = getLoginName();

        logger.info(MessageFormat.format("mobile assign coupon user:{0},begin time:{1}", loginName, DateTime.now().toString()));
        couponAssignmentService.asyncAssignUserCoupon(loginName, Lists.newArrayList(
                UserGroup.ALL_USER,
                UserGroup.INVESTED_USER,
                UserGroup.REGISTERED_NOT_INVESTED_USER,
                UserGroup.NOT_ACCOUNT_NOT_INVESTED_USER,
                UserGroup.STAFF,
                UserGroup.STAFF_RECOMMEND_LEVEL_ONE,
                UserGroup.AGENT,
                UserGroup.CHANNEL,
                UserGroup.NEW_REGISTERED_USER,
                UserGroup.IMPORT_USER,
                UserGroup.MEMBERSHIP_V0,
                UserGroup.MEMBERSHIP_V1,
                UserGroup.MEMBERSHIP_V2,
                UserGroup.MEMBERSHIP_V3,
                UserGroup.MEMBERSHIP_V4,
                UserGroup.MEMBERSHIP_V5));
        logger.info(MessageFormat.format("mobile assign coupon user:{0},end time:{1}", loginName, DateTime.now().toString()));
        return new BaseResponseDto(ReturnMessage.SUCCESS);
    }

}
