package com.tuotiansudai.api.service.v1_0;


import com.tuotiansudai.api.dto.v1_0.BaseResponseDto;
import com.tuotiansudai.api.dto.v1_0.InvestExperienceResponseDto;
import com.tuotiansudai.api.dto.v1_0.InvestRequestDto;

public interface MobileAppExperienceInvestService {

    BaseResponseDto<InvestExperienceResponseDto> experienceInvest(InvestRequestDto investRequestDto);

}
