package com.tuotiansudai.api.service.v1_0;


import com.tuotiansudai.api.dto.v1_0.BaseParamDto;
import com.tuotiansudai.api.dto.v1_0.BaseResponseDto;
import com.tuotiansudai.api.dto.v1_0.ReferrerStatisticsResponseDataDto;

public interface MobileAppReferrerStatisticsService {


    BaseResponseDto<ReferrerStatisticsResponseDataDto> getReferrerStatistics(BaseParamDto paramDto);


}
