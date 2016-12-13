package com.tuotiansudai.activity.controller;

import com.google.common.base.Strings;
import com.tuotiansudai.activity.repository.dto.DrawLotteryResultDto;
import com.tuotiansudai.activity.service.HeadlinesTodayPrizeService;
import com.tuotiansudai.spring.LoginUserInfo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(path = "/today-headlines")
public class HeadlinesTodayController {
    @Autowired
    private HeadlinesTodayPrizeService headlinesTodayPrizeService;

    private final static Logger logger = Logger.getLogger(HeadlinesTodayController.class);

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView headlinesToday() {
        System.out.println("ssdfsdfsdfd");
        ModelAndView modelAndView = new ModelAndView("/activities/today-headlines", "responsive", true);
        modelAndView.addObject("userStatus", headlinesTodayPrizeService.userStatus(LoginUserInfo.getMobile()));
        return modelAndView;
    }

    @ResponseBody
    @RequestMapping(value = "/draw", method = RequestMethod.POST)
    public DrawLotteryResultDto headlinesTodayDrawPrize(@RequestParam(value = "mobile", required = false) String mobile) {
        return headlinesTodayPrizeService.drawLotteryPrize(Strings.isNullOrEmpty(LoginUserInfo.getMobile()) ? mobile : LoginUserInfo.getMobile());
    }

}
