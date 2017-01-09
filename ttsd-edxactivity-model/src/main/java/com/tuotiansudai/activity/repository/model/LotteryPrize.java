package com.tuotiansudai.activity.repository.model;

import com.google.common.collect.Lists;

import java.util.List;

public enum LotteryPrize {
    TOURISM("华东旅游大奖",PrizeType.CONCRETE,ActivityCategory.AUTUMN_PRIZE,0),
    MANGO_CARD_100("100元芒果卡",PrizeType.CONCRETE,ActivityCategory.AUTUMN_PRIZE,0.5),
    LUXURY("奢侈品大奖",PrizeType.CONCRETE,ActivityCategory.AUTUMN_PRIZE,0),
    PORCELAIN_CUP("青花瓷杯子",PrizeType.CONCRETE,ActivityCategory.AUTUMN_PRIZE,0.5),
    RED_ENVELOPE_100("100元现金红包",PrizeType.VIRTUAL,ActivityCategory.AUTUMN_PRIZE,20),
    RED_ENVELOPE_50("50元现金红包",PrizeType.VIRTUAL,ActivityCategory.AUTUMN_PRIZE,25),
    INTEREST_COUPON_5("0.5%加息券",PrizeType.VIRTUAL,ActivityCategory.AUTUMN_PRIZE,25),
    INTEREST_COUPON_2("0.2%加息券",PrizeType.VIRTUAL,ActivityCategory.AUTUMN_PRIZE,29.5),

    //1000积分抽奖
    BICYCLE_XM("小米（MI）九号平衡车",PrizeType.CONCRETE,ActivityCategory.POINT_DRAW_1000,0),
    MASK("防雾霾骑行口罩",PrizeType.CONCRETE,ActivityCategory.POINT_DRAW_1000,3),
    LIPSTICK("屈臣氏润唇膏",PrizeType.CONCRETE,ActivityCategory.POINT_DRAW_1000,3),
    PORCELAIN_CUP_BY_1000("青花瓷杯子",PrizeType.CONCRETE,ActivityCategory.POINT_DRAW_1000,1.5),
    PHONE_BRACKET("懒人手机支架",PrizeType.CONCRETE,ActivityCategory.POINT_DRAW_1000,4.5),
    PHONE_CHARGE_10("10元话费",PrizeType.CONCRETE,ActivityCategory.POINT_DRAW_1000,3),
    RED_ENVELOPE_10("10元投资红包",PrizeType.VIRTUAL,ActivityCategory.POINT_DRAW_1000,45),
    INTEREST_COUPON_2_POINT_DRAW("0.2%加息券",PrizeType.VIRTUAL,ActivityCategory.POINT_DRAW_1000,40),

    //10000积分抽奖
    IPHONE7_128G("iPhone 7手机128G",PrizeType.CONCRETE,ActivityCategory.POINT_DRAW_10000,0),
    DELAYED_ACTION("通用自拍杆",PrizeType.CONCRETE,ActivityCategory.POINT_DRAW_10000,3),
    U_DISH("拓天速贷U盘",PrizeType.CONCRETE,ActivityCategory.POINT_DRAW_10000,3),
    PHONE_CHARGE_20("20元话费",PrizeType.CONCRETE,ActivityCategory.POINT_DRAW_10000,15),
    HEADREST("车家两用U型头枕",PrizeType.CONCRETE,ActivityCategory.POINT_DRAW_10000,10),
    IQIYI_MEMBERSHIP_30("爱奇艺会员月卡",PrizeType.CONCRETE,ActivityCategory.POINT_DRAW_10000,15),
    RED_ENVELOPE_50_POINT_DRAW("50元投资红包",PrizeType.VIRTUAL,ActivityCategory.POINT_DRAW_10000,24),
    INTEREST_COUPON_5_POINT_DRAW("0.5%加息券",PrizeType.VIRTUAL,ActivityCategory.POINT_DRAW_10000,30),

    //国庆活动
    MEMBERSHIP_V5("一个月V5会员体验",PrizeType.MEMBERSHIP,ActivityCategory.NATIONAL_PRIZE,25),
    RED_INVEST_15("15元投资红包",PrizeType.VIRTUAL,ActivityCategory.NATIONAL_PRIZE,30),
    RED_INVEST_50("50元投资红包",PrizeType.VIRTUAL,ActivityCategory.NATIONAL_PRIZE,30),
    TELEPHONE_FARE_10("10元话费",PrizeType.CONCRETE,ActivityCategory.NATIONAL_PRIZE,5),
    IQIYI_MEMBERSHIP("一个月爱奇艺会员",PrizeType.CONCRETE,ActivityCategory.NATIONAL_PRIZE,4),
    CINEMA_TICKET("电影票一张",PrizeType.CONCRETE,ActivityCategory.NATIONAL_PRIZE,3),
    FLOWER_CUP("青花瓷杯子",PrizeType.CONCRETE,ActivityCategory.NATIONAL_PRIZE,3),
    IPHONE_7("iphone7",PrizeType.CONCRETE,ActivityCategory.NATIONAL_PRIZE,0),

    //双11狂欢
    M1_PHONE("锤子M1手机(预定)",PrizeType.CONCRETE,ActivityCategory.CARNIVAL_ACTIVITY,0),
    HUMIDIFIER("小熊加湿器",PrizeType.CONCRETE,ActivityCategory.CARNIVAL_ACTIVITY,1),
    HAIR_DRIER("飞科电吹风机",PrizeType.CONCRETE,ActivityCategory.CARNIVAL_ACTIVITY,1),
    IQIYI_MEMBERSHIP_REF_CARNIVAL("爱奇艺会员",PrizeType.CONCRETE,ActivityCategory.CARNIVAL_ACTIVITY,5),
    TELEPHONE_FARE_10_REF_CARNIVAL("10元话费",PrizeType.CONCRETE,ActivityCategory.CARNIVAL_ACTIVITY,5),
    BAMBOO_CHARCOAL_PACKAGE("卡通汽车竹炭包",PrizeType.CONCRETE,ActivityCategory.CARNIVAL_ACTIVITY,8),
    INTEREST_COUPON_5_POINT_DRAW_REF_CARNIVAL("0.5加息券",PrizeType.VIRTUAL,ActivityCategory.CARNIVAL_ACTIVITY,41),
    RED_ENVELOPE_50_POINT_DRAW_REF_CARNIVAL("50元红包",PrizeType.VIRTUAL,ActivityCategory.CARNIVAL_ACTIVITY,39),

    //元旦活动
    MINI_REFRIGERATOR("迷你冰箱",PrizeType.CONCRETE,ActivityCategory.ANNUAL_ACTIVITY,0),
    ANNUAL_U_DISK("拓天速贷U盘",PrizeType.CONCRETE,ActivityCategory.ANNUAL_ACTIVITY,1),
    BOLSTER("文字君表情抱枕",PrizeType.CONCRETE,ActivityCategory.ANNUAL_ACTIVITY,1),
    RED_ENVELOPE_5("5.8元红包",PrizeType.VIRTUAL,ActivityCategory.ANNUAL_ACTIVITY,3),
    RED_ENVELOPE_3("3.8元红包",PrizeType.VIRTUAL,ActivityCategory.ANNUAL_ACTIVITY,5),
    RED_ENVELOPE_18("18.8元红包",PrizeType.VIRTUAL,ActivityCategory.ANNUAL_ACTIVITY,43),
    RED_ENVELOPE_8("8.8元红包",PrizeType.VIRTUAL,ActivityCategory.ANNUAL_ACTIVITY,45),
    INTEREST_COUPON_2_NEW_YEARS("0.2加息券",PrizeType.VIRTUAL,ActivityCategory.ANNUAL_ACTIVITY,2),

    //圣诞节活动
    MI_NOTE2_PHONE("小米Note2",PrizeType.CONCRETE,ActivityCategory.CHRISTMAS_ACTIVITY,0),
    ALOE_CAPSULE("芦荟凝胶",PrizeType.CONCRETE,ActivityCategory.CHRISTMAS_ACTIVITY,5),
    U_DISK("拓天速贷u盘",PrizeType.CONCRETE,ActivityCategory.CHRISTMAS_ACTIVITY,5),
    PHONE_FARE_10_REF_CARNIVAL("10元话费",PrizeType.CONCRETE,ActivityCategory.CHRISTMAS_ACTIVITY,15),
    ELK_PILLOW("麋鹿抱枕",PrizeType.CONCRETE,ActivityCategory.CHRISTMAS_ACTIVITY,10),
    JOHNSON_HAND_CREAM("强生护手霜",PrizeType.CONCRETE,ActivityCategory.CHRISTMAS_ACTIVITY,10),
    PUMPKIN_PILLOW("南瓜抱枕",PrizeType.CONCRETE,ActivityCategory.CHRISTMAS_ACTIVITY,10),
    RED_ENVELOPE_20_POINT_DRAW_REF_CARNIVAL("20元投资红包",PrizeType.VIRTUAL,ActivityCategory.CHRISTMAS_ACTIVITY,45),

    //今日头条拉新
    T1_PHONE("锤子M1手机(预订)",PrizeType.CONCRETE,ActivityCategory.HEADLINES_TODAY_ACTIVITY,0),
    BAMBOO_BAG("卡通汽车竹炭包",PrizeType.CONCRETE,ActivityCategory.HEADLINES_TODAY_ACTIVITY,8),
    FLYCO_HAIR_DRIER("飞科电吹风机",PrizeType.CONCRETE,ActivityCategory.HEADLINES_TODAY_ACTIVITY,1),
    IQIYI_MEMBERSHIP_MONTH_REF_CARNIVAL("爱奇艺会员月卡",PrizeType.CONCRETE,ActivityCategory.HEADLINES_TODAY_ACTIVITY,4),
    TELEPHONE_FARE_10_YUAN_REF_CARNIVAL("10元话费",PrizeType.CONCRETE,ActivityCategory.HEADLINES_TODAY_ACTIVITY,11),
    QQ_EMOTICON_PILLOW("QQ表情靠枕",PrizeType.CONCRETE,ActivityCategory.HEADLINES_TODAY_ACTIVITY,8),
    INSPISSATE_TOWEL("加厚纯棉毛巾",PrizeType.CONCRETE,ActivityCategory.HEADLINES_TODAY_ACTIVITY,13),
    RED_ENVELOPE_50_YUAN_DRAW_REF_CARNIVAL("50元红包",PrizeType.VIRTUAL,ActivityCategory.HEADLINES_TODAY_ACTIVITY,55),

    //积分商城抽奖
    POINT_SHOP_RED_ENVELOPE_10("10元红包",PrizeType.VIRTUAL,ActivityCategory.POINT_SHOP_DRAW_1000,12),
    POINT_SHOP_RED_ENVELOPE_50("50元红包",PrizeType.VIRTUAL,ActivityCategory.POINT_SHOP_DRAW_1000,12),
    POINT_SHOP_INTEREST_COUPON_2("0.2%加息劵",PrizeType.VIRTUAL,ActivityCategory.POINT_SHOP_DRAW_1000,10),
    POINT_SHOP_INTEREST_COUPON_5("0.5%加息劵",PrizeType.VIRTUAL,ActivityCategory.POINT_SHOP_DRAW_1000,9),
    POINT_SHOP_POINT_500("500积分",PrizeType.VIRTUAL,ActivityCategory.POINT_SHOP_DRAW_1000,48),
    POINT_SHOP_POINT_3000("3000积分",PrizeType.VIRTUAL,ActivityCategory.POINT_SHOP_DRAW_1000,8),
    POINT_SHOP_PHONE_CHARGE_10("10元话费",PrizeType.CONCRETE,ActivityCategory.POINT_SHOP_DRAW_1000,1),
    POINT_SHOP_JD_10("100元京东E卡",PrizeType.CONCRETE,ActivityCategory.POINT_SHOP_DRAW_1000,0);

    String description;
    PrizeType prizeType;
    ActivityCategory activityCategory;
    double rate;

    LotteryPrize(String description, PrizeType prizeType, ActivityCategory activityCategory, double rate){
        this.description = description;
        this.activityCategory = activityCategory;
        this.prizeType = prizeType;
        this.rate = rate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public PrizeType getPrizeType() {
        return prizeType;
    }

    public void setPrizeType(PrizeType prizeType) {
        this.prizeType = prizeType;
    }

    public ActivityCategory getActivityCategory() {
        return activityCategory;
    }

    public void setActivityCategory(ActivityCategory activityCategory) {
        this.activityCategory = activityCategory;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public static List<LotteryPrizeView> getActivityPrize(ActivityCategory activityCategory){
        List list = Lists.newArrayList();
        LotteryPrize[] lotteryPrizes = LotteryPrize.values();
        for(LotteryPrize lotteryPrize : lotteryPrizes) {
            if (activityCategory.equals(lotteryPrize.getActivityCategory())) {
                list.add(new LotteryPrizeView(lotteryPrize.name(), lotteryPrize.getDescription()));
            }
        }
        return list;
    }
}
