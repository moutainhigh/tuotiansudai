package com.tuotiansudai.api.service.v1_0.impl;


import com.tuotiansudai.api.dto.v1_0.BaseResponseDto;
import com.tuotiansudai.api.dto.v1_0.NoPasswordInvestTurnOffRequestDto;
import com.tuotiansudai.api.dto.v1_0.ReturnMessage;
import com.tuotiansudai.api.service.v1_0.MobileAppNoPasswordInvestTurnOffService;
import com.tuotiansudai.api.util.AppVersionUtil;
import com.tuotiansudai.repository.mapper.AccountMapper;
import com.tuotiansudai.repository.mapper.UserMapper;
import com.tuotiansudai.repository.model.AccountModel;
import com.tuotiansudai.repository.model.CaptchaType;
import com.tuotiansudai.repository.model.UserModel;
import com.tuotiansudai.service.SmsCaptchaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MobileAppNoPasswordInvestTurnOffServiceImpl implements MobileAppNoPasswordInvestTurnOffService {
    @Autowired
    private AccountMapper accountMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private SmsCaptchaService smsCaptchaService;

    @Override
    public BaseResponseDto noPasswordInvestTurnOff(NoPasswordInvestTurnOffRequestDto noPasswordInvestTurnOffRequestDto, String ip) {
        BaseResponseDto baseResponseDto = new BaseResponseDto();
        String loginName = noPasswordInvestTurnOffRequestDto.getBaseParam().getUserId();

        if (AppVersionUtil.low == AppVersionUtil.compareVersion()) {
            UserModel userModel = userMapper.findByLoginName(loginName);
            boolean verifyCaptchaFailed = !smsCaptchaService.verifyMobileCaptcha(userModel.getMobile(), noPasswordInvestTurnOffRequestDto.getCaptcha(), CaptchaType.NO_PASSWORD_INVEST);
            if (verifyCaptchaFailed) {
                baseResponseDto.setCode(ReturnMessage.SMS_CAPTCHA_ERROR.getCode());
                baseResponseDto.setMessage(ReturnMessage.SMS_CAPTCHA_ERROR.getMsg());
            }
        }

        AccountModel accountModel = accountMapper.findByLoginName(loginName);
        accountModel.setNoPasswordInvest(false);
        accountMapper.update(accountModel);
        baseResponseDto.setCode(ReturnMessage.SUCCESS.getCode());
        baseResponseDto.setMessage(ReturnMessage.SUCCESS.getMsg());

        return baseResponseDto;
    }
}
