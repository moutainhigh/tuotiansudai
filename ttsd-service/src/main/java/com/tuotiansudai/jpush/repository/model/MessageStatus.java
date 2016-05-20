package com.tuotiansudai.jpush.repository.model;

public enum MessageStatus {
    WAIT_AUDIT("待审核"),
    WILL_SEND("即将推送"),
    REJECTED("已驳回"),
    ENABLED("已启用"),
    DISABLED("已暂停"),
    SEND_SUCCESS("已发送"),
    SEND_FAIL("发送失败");

    private final String description;

    MessageStatus(String description){
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
