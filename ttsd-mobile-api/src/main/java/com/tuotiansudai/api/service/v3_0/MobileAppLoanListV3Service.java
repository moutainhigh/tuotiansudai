package com.tuotiansudai.api.service.v3_0;

import com.tuotiansudai.api.dto.v1_0.BaseResponseDto;
import com.tuotiansudai.api.dto.v3_0.LoanListResponseDataDto;

public interface MobileAppLoanListV3Service {
    BaseResponseDto<LoanListResponseDataDto> generateIndexLoan(String loginName);
}
