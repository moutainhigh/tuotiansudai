package com.tuotiansudai.rest.dto.request;

import com.tuotiansudai.repository.model.Source;

import java.io.Serializable;

public class UserRestRegisterRequestDto implements Serializable {
    private String mobile;
    private String password;
    private String referrer;
    private String channel;
    private Source source;

    public UserRestRegisterRequestDto(String mobile, String password, String referrer, String channel, Source source) {
        this.mobile = mobile;
        this.password = password;
        this.referrer = referrer;
        this.channel = channel;
        this.source = source;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getReferrer() {
        return referrer;
    }

    public void setReferrer(String referrer) {
        this.referrer = referrer;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public Source getSource() {
        return source;
    }

    public void setSource(Source source) {
        this.source = source;
    }
}
