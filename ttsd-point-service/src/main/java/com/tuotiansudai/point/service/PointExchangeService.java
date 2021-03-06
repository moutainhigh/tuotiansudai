package com.tuotiansudai.point.service;

import com.tuotiansudai.dto.BasePaginationDataDto;
import com.tuotiansudai.point.repository.model.ProductOrderViewDto;

public interface PointExchangeService {

    BasePaginationDataDto<ProductOrderViewDto> findProductOrderListByLoginNamePagination(String loginName, int index, int pageSize);

}
