package com.tuotiansudai.service;

import com.tuotiansudai.dto.Granularity;
import com.tuotiansudai.repository.model.KeyValueModel;

import java.util.Date;
import java.util.List;

public interface BusinessIntelligenceService {
    List<KeyValueModel> queryUserRegisterTrend(Granularity granularity, Date startTime, Date endTime);

    List<KeyValueModel> queryUserDistribution(Date startTime, Date endTime);
}
