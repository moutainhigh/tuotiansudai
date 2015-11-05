package com.tuotiansudai.console.controller;

import com.google.common.collect.Lists;
import com.tuotiansudai.dto.BaseDto;
import com.tuotiansudai.dto.BasePaginationDataDto;
import com.tuotiansudai.repository.model.WithdrawStatus;
import com.tuotiansudai.service.WithdrawService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;


@Controller
public class WithdrawController {

    @Autowired
    WithdrawService withdrawService;

    @RequestMapping(value = "/withdraw", method = RequestMethod.GET)
    public ModelAndView getWithdrawList(@RequestParam(value = "withdrawId", required = false) String withdrawId,
                                        @RequestParam(value = "loginName", required = false) String loginName,
                                        @RequestParam(value = "startTime", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date startTime,
                                        @RequestParam(value = "endTime", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date endTime,
                                        @RequestParam(value = "status", required = false) WithdrawStatus status,
                                        @RequestParam(value = "index", defaultValue = "1", required = false) int index,
                                        @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize) {

        ModelAndView modelAndView = new ModelAndView("/withdraw");
        BaseDto<BasePaginationDataDto> baseDto = withdrawService.findWithdrawPagination(withdrawId,
                loginName,
                status,
                index,
                pageSize,
                startTime,
                endTime);

        modelAndView.addObject("baseDto", baseDto);
        modelAndView.addObject("withdrawStatusList", Lists.newArrayList(WithdrawStatus.values()));
        modelAndView.addObject("index", index);
        modelAndView.addObject("pageSize", pageSize);
        modelAndView.addObject("withdrawId", withdrawId);
        modelAndView.addObject("loginName", loginName);
        modelAndView.addObject("startTime", startTime);
        modelAndView.addObject("endTime", endTime);
        modelAndView.addObject("status", status);
        if (status != null) {
            modelAndView.addObject("withdrawStatus", status);
        }
        return modelAndView;
    }
}
