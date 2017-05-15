package com.tuotiansudai.activity.controller;

import com.tuotiansudai.activity.service.DragonBoatFestivalService;
import com.tuotiansudai.coupon.service.CouponAssignmentService;
import com.tuotiansudai.dto.RegisterUserDto;
import com.tuotiansudai.repository.model.Source;
import com.tuotiansudai.service.UserService;
import com.tuotiansudai.spring.LoginUserInfo;
import com.tuotiansudai.spring.security.MyAuthenticationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.text.MessageFormat;

@Component
@RequestMapping(value = "/activity/wechat/dragon")
public class DragonBoatFestivalController {

    public static final Logger logger = LoggerFactory.getLogger(DragonBoatFestivalController.class);

    @Autowired
    private DragonBoatFestivalService dragonBoatFestivalService;

    @Autowired
    private UserService userService;

    @Autowired
    private MyAuthenticationUtil myAuthenticationUtil;

    @Autowired
    private CouponAssignmentService couponAssignmentService;

    // 微信公众号回复打卡页面
    @RequestMapping(value = "/punchCard", method = RequestMethod.GET)
    public ModelAndView wechatFirstPage() {
        String loginName = LoginUserInfo.getLoginName();
        String exchangeCode = dragonBoatFestivalService.getCouponExchangeCode(loginName);

        ModelAndView mav = new ModelAndView("/wechat-dragon");
        mav.addObject("exchangeCode", exchangeCode); // null表示今天已领过
        mav.addObject("loginName", loginName);
        return mav;
    }

    // 分享落地页
    @RequestMapping(value = "/shareLanding", method = RequestMethod.GET)
    public ModelAndView shareLanding(HttpServletRequest request) {
        String loginName = LoginUserInfo.getLoginName();
        String sharerLoginName = request.getParameter("sharer");

        ModelAndView mav = new ModelAndView("/wechat-dragon");
        mav.addObject("loginName", loginName);
        mav.addObject("sharerLoginName", sharerLoginName);
        return mav;
    }

    // 新用户注册
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public boolean userRegister(@RequestParam(value = "referrer", required = false) String referrer,
                                @RequestParam(value = "captcha", required = false) String captcha,
                                @RequestParam(value = "password", required = false) String password,
                                @RequestParam(value = "mobile", required = false) String mobile,
                                @RequestParam(value = "channel", required = false) String channel) {

        boolean isRegisterSuccess;
        RegisterUserDto registerUserDto = new RegisterUserDto();
        registerUserDto.setMobile(mobile);
        registerUserDto.setChannel(channel);
        registerUserDto.setReferrer(referrer);
        registerUserDto.setCaptcha(captcha);
        registerUserDto.setPassword(password);
        logger.info(MessageFormat.format("[Dragon Boat Register User {}] controller starting...", registerUserDto.getMobile()));
        isRegisterSuccess = this.userService.registerUser(registerUserDto);
        logger.info(MessageFormat.format("[Dragon Boat Register User {}] controller invoked service ({})", registerUserDto.getMobile(), String.valueOf(isRegisterSuccess)));

        if (isRegisterSuccess) {
            logger.info(MessageFormat.format("[Dragon Boat Register User {}] authenticate starting...", registerUserDto.getMobile()));
            myAuthenticationUtil.createAuthentication(registerUserDto.getMobile(), Source.WEB);
            logger.info(MessageFormat.format("[Dragon Boat Register User {}] authenticate completed", registerUserDto.getMobile()));

            logger.info(MessageFormat.format("[Dragon Boat Register User {}] assign weiXin share ￥10 red enveloper ", registerUserDto.getMobile()));
            couponAssignmentService.assignUserCoupon(registerUserDto.getMobile(), 421);

            logger.info(MessageFormat.format("[Dragon Boat Register User {}] add invite-new-user-count for {}", registerUserDto.getMobile(), referrer));
            dragonBoatFestivalService.addInviteNewUserCount(referrer);
        }
        return isRegisterSuccess;
    }


}
