package com.tuotiansudai.service;


import com.tuotiansudai.dto.BaseListDataDto;
import com.tuotiansudai.repository.model.HeroRankingView;

import java.util.Date;
import java.util.List;

public interface HeroRankingService {

    BaseListDataDto findHeroRankingByReferrer(Date tradingTime, String loginName, int index, int pageSize);

    Integer findHeroRankingByReferrerLoginName(String loginName);

    List<HeroRankingView> obtainHeroRanking(Date tradingTime);

    List<HeroRankingView> obtainHeroRankingByLoginName(Date tradingTime,String loginName);

}
