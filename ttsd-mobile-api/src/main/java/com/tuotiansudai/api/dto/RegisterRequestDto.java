package com.tuotiansudai.api.dto;

import com.tuotiansudai.dto.RegisterUserDto;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Pattern;

public class RegisterRequestDto extends BaseParamDto {
    @NotEmpty
    @Pattern(regexp = "(?!^\\d+$)^([a-zA-Z0-9]{5,25})$")
    private String userName;
    @NotEmpty
    @Pattern(regexp = "^1\\d{10}$")
    private String phoneNum;
    @NotEmpty
    @Pattern(regexp = "^[0-9]{6}$")
    private String captcha;
    @NotEmpty
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-zA-Z])([a-zA-Z0-9]{6,20})$")
    private String password;
    private String referrer;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
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

    public RegisterUserDto convertToRegisterUserDto(){
        RegisterUserDto registerUserDto = new RegisterUserDto();
        registerUserDto.setLoginName(this.getUserName());
        registerUserDto.setMobile(this.getPhoneNum());
        registerUserDto.setPassword(this.getPassword());
        registerUserDto.setCaptcha(this.getCaptcha());
        registerUserDto.setReferrer(this.getReferrer());
        return registerUserDto;
    }

}
