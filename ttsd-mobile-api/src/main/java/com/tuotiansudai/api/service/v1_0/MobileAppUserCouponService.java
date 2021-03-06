package com.tuotiansudai.api.service.v1_0;

import com.tuotiansudai.api.dto.v1_0.BaseResponseDto;
import com.tuotiansudai.api.dto.v1_0.UserCouponListResponseDataDto;
import com.tuotiansudai.api.dto.v1_0.UserCouponRequestDto;

public interface MobileAppUserCouponService {
    BaseResponseDto<UserCouponListResponseDataDto> getUserCoupons(UserCouponRequestDto dto);
}
