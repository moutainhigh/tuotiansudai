package com.tuotiansudai.api.controller.v1_0;

import com.tuotiansudai.api.dto.v1_0.BaseResponseDto;
import com.tuotiansudai.api.dto.v1_0.ReturnMessage;
import com.tuotiansudai.api.dto.v1_0.SendSmsCompositeRequestDto;
import com.tuotiansudai.api.dto.v1_0.VerifyCaptchaRequestDto;
import com.tuotiansudai.api.service.v1_0.MobileAppSendSmsService;
import com.tuotiansudai.api.util.CommonUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@Api(description = "发送短信验证码")
public class MobileAppSendSmsController extends MobileAppBaseController{

    @Autowired
    private MobileAppSendSmsService mobileAppSendSmsService;

    @RequestMapping(value = "/sms-captcha/send", method = RequestMethod.POST)
    @ApiOperation("发送短信验证码")
    public BaseResponseDto sendSms(@Valid @RequestBody SendSmsCompositeRequestDto sendSmsCompositeRequestDto,BindingResult bindingResult,HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            String errorCode = bindingResult.getFieldError().getDefaultMessage();
            String errorMessage = ReturnMessage.getErrorMsgByCode(errorCode);
            return new BaseResponseDto(errorCode, errorMessage);
        } else {
            sendSmsCompositeRequestDto.getBaseParam().setUserId(getLoginName());
            String remoteIp = CommonUtils.getRemoteHost(request);
            return mobileAppSendSmsService.sendSms(sendSmsCompositeRequestDto,remoteIp);
        }
    }
    @RequestMapping(value = "/validatecaptcha", method = RequestMethod.POST)
    @ApiOperation("验证手机验证码是否正确")
    public BaseResponseDto validateAuthCode(@Valid @RequestBody VerifyCaptchaRequestDto verifyCaptchaRequestDto,BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String errorCode = bindingResult.getFieldError().getDefaultMessage();
            String errorMessage = ReturnMessage.getErrorMsgByCode(errorCode);
            return new BaseResponseDto(errorCode, errorMessage);
        } else {
            return mobileAppSendSmsService.validateCaptcha(verifyCaptchaRequestDto);
        }
    }
}
