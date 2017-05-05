package com.tuotiansudai.activity.service;

import com.tuotiansudai.activity.repository.dto.WechatLotteryDto;
import com.tuotiansudai.activity.repository.mapper.UserLotteryPrizeMapper;
import com.tuotiansudai.activity.repository.model.ActivityCategory;
import com.tuotiansudai.activity.repository.model.LotteryPrize;
import com.tuotiansudai.activity.repository.model.UserLotteryPrizeModel;
import com.tuotiansudai.activity.repository.model.UserLotteryPrizeView;
import com.tuotiansudai.coupon.service.CouponAssignmentService;
import com.tuotiansudai.dto.BaseDto;
import com.tuotiansudai.repository.mapper.UserMapper;
import com.tuotiansudai.repository.model.UserModel;
import com.tuotiansudai.util.RedisWrapperClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class WechatLotteryService {

    private static final Logger logger = LoggerFactory.getLogger(WechatLotteryService.class);

    private final RedisWrapperClient redisWrapperClient = RedisWrapperClient.getInstance();

    private static final String WECHAT_LOTTERY_COUNT_KEY = "WECHAT_LOTTERY_COUNT:";

    private static final int THIRTY_DAYS = 60 * 60 * 24 * 30;

    private static final String WECHAT_PRIZE_1_KEY = "WECHAT_PRIZE_1";

    private static final String WECHAT_PRIZE_2_KEY = "WECHAT_PRIZE_2";

    @Autowired
    private CouponAssignmentService couponAssignmentService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserLotteryPrizeMapper userLotteryPrizeMapper;

    public BaseDto<WechatLotteryDto> drawLottery(String loginName) {

        BaseDto<WechatLotteryDto> baseDto = new BaseDto<>();
        baseDto.setSuccess(true);
        WechatLotteryDto dto = new WechatLotteryDto();
        dto.setStatus(true);
        dto.setReturnCode(0);
        baseDto.setData(dto);

        Long leftDrawCount = redisWrapperClient.decrEx(WECHAT_LOTTERY_COUNT_KEY + loginName, THIRTY_DAYS, 1);
        if (leftDrawCount < 0) {
            redisWrapperClient.del(WECHAT_LOTTERY_COUNT_KEY + loginName);
            logger.error("no draw count left, someone is cheating. loginName:{0}", loginName);
            dto.setReturnCode(1);
            dto.setMessage("您的抽奖次数已用完，投资可以获得更多的抽奖机会！");
            dto.setStatus(false);
            return baseDto;
        }

        LotteryPrize prize;
        long random = (long) (Math.random() * 1000000);
        long mod = random % 100;
        if (mod == 0) {
            if (redisWrapperClient.exists(WECHAT_PRIZE_1_KEY)) {
                prize = LotteryPrize.WECHAT_LOTTERY_RED_ENVELOP_20;
            } else {
                redisWrapperClient.setex(WECHAT_PRIZE_1_KEY, THIRTY_DAYS, "1");
                prize = LotteryPrize.WECHAT_LOTTERY_BEDCLOTHES;
            }
        } else if (mod == 1 || mod == 2) {
            if (redisWrapperClient.exists(WECHAT_PRIZE_2_KEY)) {
                prize = LotteryPrize.WECHAT_LOTTERY_RED_ENVELOP_20;
            } else {
                redisWrapperClient.setex(WECHAT_PRIZE_2_KEY, THIRTY_DAYS, "1");
                prize = LotteryPrize.WECHAT_LOTTERY_BAG;
            }
        } else if (mod < 10) {
            prize = LotteryPrize.WECHAT_LOTTERY_HEADGEAR;
        } else if (mod < 30) {
            prize = LotteryPrize.WECHAT_LOTTERY_TOWEL;
        } else {
            prize = LotteryPrize.WECHAT_LOTTERY_RED_ENVELOP_20;
        }

        if (prize == LotteryPrize.WECHAT_LOTTERY_RED_ENVELOP_20) {
            sendRedEnvelopCoupon20(loginName);
        }
        dto.setWechatLotteryPrize(prize);

        UserModel user = userMapper.findByLoginName(loginName);
        UserLotteryPrizeModel model = new UserLotteryPrizeModel(user.getMobile(), loginName, user.getUserName(), prize, new Date(), ActivityCategory.WECHAT_FIRST_INVEST_PRIZE);
        userLotteryPrizeMapper.create(model);
        return baseDto;
    }

    private void sendRedEnvelopCoupon20(String loginName) {
        couponAssignmentService.assignUserCoupon(loginName, 409L);
    }

    public int getLeftDrawCount(String loginName) {
        String count = redisWrapperClient.get(WECHAT_LOTTERY_COUNT_KEY + loginName);
        return count == null ? 0 : Integer.parseInt(count);
    }


    public List<UserLotteryPrizeView> getLotteryListTop20() {
        return userLotteryPrizeMapper.findUserLotteryPrizeViews(null, null, ActivityCategory.WECHAT_FIRST_INVEST_PRIZE, null, null, 0, 20);
    }

    public List<UserLotteryPrizeView> getMyLotteryList(String mobile) {
        return userLotteryPrizeMapper.findUserLotteryPrizeViews(mobile, null, ActivityCategory.WECHAT_FIRST_INVEST_PRIZE, null, null, null, null);
    }

}
