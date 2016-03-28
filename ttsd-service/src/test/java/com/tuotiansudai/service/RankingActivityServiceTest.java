package com.tuotiansudai.service;


import com.tuotiansudai.client.RedisWrapperClient;
import com.tuotiansudai.dto.BaseDto;
import com.tuotiansudai.dto.ranking.DrawLotteryDto;
import com.tuotiansudai.dto.ranking.PrizeWinnerDto;
import com.tuotiansudai.dto.ranking.UserTianDouRecordDto;
import com.tuotiansudai.repository.TianDouPrize;
import com.tuotiansudai.service.impl.RankingActivityServiceImpl;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
@Transactional
public class RankingActivityServiceTest {

    @Autowired
    RankingActivityService rankingActivityService;

    @Autowired
    private RedisWrapperClient redisWrapperClient;

    @Before
    public void init() {
        clearRankingDataInRedis();
    }

    @After
    public void cleanRedis() {
//        clearRankingDataInRedis();
    }

    @Test
    public void shouldDrawPrizeNotEnoughTianDou() {
        clearRankingDataInRedis();

        String loginName = "ranking_test_1";
        String mobile = "13900001111";

        BaseDto<DrawLotteryDto> baseDto = rankingActivityService.drawTianDouPrize(loginName, mobile);

        assert (baseDto.isSuccess() == false);
        assert (baseDto.getData().getReturnCode() == 1);
    }

    @Test
    public void shouldDrawPrizeSuccess() {
        clearRankingDataInRedis();

        String loginName = "shenjiaojiao";
        String mobile = "13900001111";

        long investAmount = 30000;
        long tianDouScore = investAmount * 3 / 12; // 3月标，7500
        String loanId = "11111111";

        mockInvestTianDou(loginName, investAmount, tianDouScore, loanId);

        BaseDto<DrawLotteryDto> baseDto = rankingActivityService.drawTianDouPrize(loginName, mobile);

        assert (baseDto.isSuccess() == true);
        assert (baseDto.getData().getStatus() == true);
        assert (baseDto.getData().getReturnCode() == 0);

        Double score = rankingActivityService.getUserScoreByLoginName(loginName);
        assert (score == 6500);

        long rank = rankingActivityService.getUserRank(loginName);
        assert (rank == 1);

        long drawCount = rankingActivityService.getDrawCount();
        assert (drawCount == 1);

        long drawUserCount = rankingActivityService.getDrawUserCount();
        assert (drawUserCount == 1);

        List<UserTianDouRecordDto> prizeList = rankingActivityService.getPrizeByLoginName(loginName);
        assert (prizeList != null && prizeList.size() == 1);

        UserTianDouRecordDto recordDto = prizeList.get(0);
        assert (recordDto.getPrize() == TianDouPrize.InterestCoupon5);

        long prizeWinnerCount = rankingActivityService.getPrizeWinnerCount(TianDouPrize.InterestCoupon5);
        assert (prizeWinnerCount == 1);

        List<PrizeWinnerDto> prizeWinnerDtoList = rankingActivityService.getPrizeWinnerList(TianDouPrize.InterestCoupon5.toString());
        assert (prizeWinnerDtoList != null && prizeWinnerDtoList.size() == 1);

        PrizeWinnerDto winnerDto = prizeWinnerDtoList.get(0);
        assert (winnerDto.getLoginName().equals(loginName));
        assert (winnerDto.getMobile().equals(mobile));

        long totalTiandou = rankingActivityService.getTotalTiandouByLoginName(loginName);
        assert (totalTiandou == tianDouScore);

        List<UserTianDouRecordDto> tianDouRecordDtoList = rankingActivityService.getTianDouRecordsByLoginName(loginName);
        assert (tianDouRecordDtoList != null && tianDouRecordDtoList.size() == 2);

        UserTianDouRecordDto userInvestRecord = tianDouRecordDtoList.get(0);
        assert (userInvestRecord.getType().equals("INVEST"));
        assert (userInvestRecord.getAmount() == investAmount);
        assert (userInvestRecord.getScore() == tianDouScore);
        assert (userInvestRecord.getDesc().equals(loanId));

        UserTianDouRecordDto userDrawRecord = tianDouRecordDtoList.get(1);
        assert (userDrawRecord.getType().equals("DRAW"));
        assert (userDrawRecord.getPrize() == TianDouPrize.InterestCoupon5);

        Map<String, List<UserTianDouRecordDto>> winnerListMap = rankingActivityService.getTianDouWinnerList();
        List<UserTianDouRecordDto> macBookList = winnerListMap.get("MacBook");
        List<UserTianDouRecordDto> iPhoneList = winnerListMap.get("iPhone");
        List<UserTianDouRecordDto> otherList = winnerListMap.get("other");
        assert (macBookList == null || macBookList.size() == 0);
        assert (iPhoneList == null || iPhoneList.size() == 0);
        assert (otherList != null && otherList.size() == 1);

        UserTianDouRecordDto userTianDouRecordDto = otherList.get(0);
        assert (userTianDouRecordDto.getPrize() == TianDouPrize.InterestCoupon5);
        assert (userTianDouRecordDto.getLoginName().equals(loginName));
    }


    private void mockInvestTianDou(String loginName, long amount, long tianDouScore, String loanId) {

        String time = DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss");

        String value = amount + "+" + tianDouScore + "+" + loanId + "+" + time;
        redisWrapperClient.lpush(RankingActivityServiceImpl.TIAN_DOU_INVEST_SCORE_RECORD + loginName, value);
        redisWrapperClient.zincrby(RankingActivityServiceImpl.TIAN_DOU_USER_SCORE_RANK, tianDouScore, loginName);
    }

    private void clearRankingDataInRedis() {
        String[] keys = new String[]{RankingActivityServiceImpl.TIAN_DOU_DRAW_COUNTER,
                RankingActivityServiceImpl.TIAN_DOU_DRAW_USER_SET,
                RankingActivityServiceImpl.TIAN_DOU_ALL_WINNER,
                RankingActivityServiceImpl.TIAN_DOU_USER_SCORE_RANK};
        redisWrapperClient.del(keys);
        redisWrapperClient.delPattern(RankingActivityServiceImpl.TIAN_DOU_WINNER_PRIZE + "*");
        redisWrapperClient.delPattern(RankingActivityServiceImpl.TIAN_DOU_PRIZE_WINNER + "*");
        redisWrapperClient.delPattern(RankingActivityServiceImpl.TIAN_DOU_INVEST_SCORE_RECORD + "*");
    }
}
