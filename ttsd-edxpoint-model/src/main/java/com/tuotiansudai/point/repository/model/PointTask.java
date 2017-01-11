package com.tuotiansudai.point.repository.model;

public enum PointTask {
    REGISTER("实名认证", "完成实名认证开通个人账户。"),
    BIND_EMAIL("绑定邮箱", "完成绑定邮箱"), //取消
    BIND_BANK_CARD("绑定银行卡", "绑定常用银行卡，赚钱快人一步。"),
    FIRST_RECHARGE("首次充值", "首次充值成功。"),
    FIRST_INVEST("首次投资", "首次投资成功。"),
    SUM_INVEST_10000("累计投资10000元", "累计投资满10000元"), //取消

    EACH_SUM_INVEST("累计投资满{0}元", "累计投资满5000 奖励100"),
    FIRST_SINGLE_INVEST("单笔投资满{0}元", "单笔投资满10000 奖励200"),
    EACH_RECOMMEND_REGISTER("每次邀请好友注册", "每邀请好友注册奖励100积分"),
    EACH_RECOMMEND_REFERRER_BANK_CARD("邀请好友绑卡", "邀请好友绑卡奖励100积分"),
    EACH_RECOMMEND_REFERRER_INVEST("邀请好友投资", "邀请好友投资奖励200积分"),
    EACH_REFERRER_INVEST("每邀请好友投资满1000元", "好友每投1000元奖1000财豆，还拿年化1%现金奖励"), //取消
    FIRST_REFERRER_INVEST("首次邀请好友投资", "首次邀请好友投资奖励50财豆"),
    FIRST_INVEST_180("首次投资180天标的", "首次投资180天标的奖励100财豆"),
    FIRST_INVEST_360("首次投资360天标的", "首次投资360天标的奖励200财豆"),
    FIRST_TURN_ON_NO_PASSWORD_INVEST("首次开通免密支付", "首次开通免密支付并成功投资奖励100财豆"),
    FIRST_TURN_ON_AUTO_INVEST("首次开通自动投标", "首次开通并成功自动投标奖励50财豆");

    private final String title;

    private final String description;

    PointTask(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public String getTitle() {
        return title;
    }
}
