package com.tuotiansudai.web.controller;

import com.google.common.base.Strings;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.tuotiansudai.dto.*;
import com.tuotiansudai.repository.model.AccountModel;
import com.tuotiansudai.repository.model.Source;
import com.tuotiansudai.service.AccountService;
import com.tuotiansudai.service.UserService;
import com.tuotiansudai.spring.LoginUserInfo;
import com.tuotiansudai.spring.security.MyAuthenticationUtil;
import com.tuotiansudai.util.IdentityNumberValidator;
import org.joda.time.DateTime;
import org.joda.time.Minutes;
import org.joda.time.Seconds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
@RequestMapping(path = "/register/account")
public class RegisterAccountController {

    @Autowired
    private UserService userService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private MyAuthenticationUtil myAuthenticationUtil;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView registerAccount(@RequestParam(name = "redirect", required = false, defaultValue = "/") String redirect) {
        ModelAndView modelAndView = new ModelAndView("/register-account", "responsive", true);
        modelAndView.addObject("redirect", redirect);
        return modelAndView;
    }

    @RequestMapping(value = "/identity-number/{identityNumber:^[1-9]\\d{13,16}[a-zA-Z0-9]{1}$}/is-exist", method = RequestMethod.GET)
    @ResponseBody
    public BaseDto<BaseDataDto> isIdentityNumberExist(@PathVariable String identityNumber) {
        boolean isExist = userService.isIdentityNumberExist(identityNumber);
        BaseDto<BaseDataDto> baseDto = new BaseDto<>();
        BaseDataDto dataDto = new BaseDataDto();
        baseDto.setData(dataDto);
        dataDto.setStatus(isExist);
        return baseDto;
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public BaseDto<HuiZuDataDto> registerAccount(@Valid @ModelAttribute RegisterAccountDto registerAccountDto) {
        if (IdentityNumberValidator.validateIdentity(registerAccountDto.getIdentityNumber())) {
            registerAccountDto.setLoginName(LoginUserInfo.getLoginName());
            registerAccountDto.setMobile(LoginUserInfo.getMobile());
            BaseDto<HuiZuDataDto> baseDto = accountService.registerAccountFromHuiZu(registerAccountDto);
            baseDto.getData().setExtraValues(Maps.newHashMap(ImmutableMap.<String, String>builder()
                    .put("referrer", Strings.isNullOrEmpty(registerAccountDto.getReferrer()) ? "" : registerAccountDto.getReferrer())
                    .build()));
            myAuthenticationUtil.createAuthentication(LoginUserInfo.getLoginName(), Source.WEB);
            return baseDto;
        }

        BaseDto<HuiZuDataDto> baseDto = new BaseDto<>();
        HuiZuDataDto dataDto = new HuiZuDataDto();
        baseDto.setData(dataDto);
        baseDto.getData().setExtraValues(Maps.newHashMap(ImmutableMap.<String, String>builder()
                .put("referrer", Strings.isNullOrEmpty(registerAccountDto.getReferrer()) ? "" : registerAccountDto.getReferrer())
                .build()));
        return baseDto;
    }

    @RequestMapping(path = "/success", method = RequestMethod.GET)
    public ModelAndView registerAccountSuccess() {
        AccountModel accountModel = accountService.findByLoginName(LoginUserInfo.getLoginName());
        if (accountModel == null) {
            return new ModelAndView("/error/404");
        }
        return new ModelAndView("/register-account-success");
    }
}
