package com.tuotiansudai.paywrapper.controller;

import com.tuotiansudai.dto.BaseDto;
import com.tuotiansudai.dto.PayDataDto;
import com.tuotiansudai.dto.PayFormDataDto;
import com.tuotiansudai.dto.RepayDto;
import com.tuotiansudai.paywrapper.service.AdvanceRepayService;
import com.tuotiansudai.paywrapper.service.NormalRepayService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;

@Controller
public class RepayController {

    private static Logger logger = Logger.getLogger(RepayController.class);

    @Autowired
    private NormalRepayService normalRepayService;

    @Autowired
    private AdvanceRepayService advanceRepayService;

    @RequestMapping(value = "/repay", method = RequestMethod.POST)
    @ResponseBody
    public BaseDto<PayFormDataDto> repay(@Valid @RequestBody RepayDto dto) {
        return dto.isAdvanced() ? advanceRepayService.generateRepayFormData(dto.getLoanId()) : normalRepayService.generateRepayFormData(dto.getLoanId());
    }

    @RequestMapping(value = "/auto-repay", method = RequestMethod.POST)
    @ResponseBody
    public BaseDto<PayDataDto> autoRepay(@RequestBody long loanRepayId) {
        BaseDto<PayDataDto> baseDto = new BaseDto<>();
        PayDataDto payDataDto = new PayDataDto();
        baseDto.setData(payDataDto);
        try {
            payDataDto.setStatus(normalRepayService.autoRepay(loanRepayId));
        } catch (Exception e) {
            payDataDto.setMessage(e.getLocalizedMessage());
            logger.error("auto repay failed", e);
        }
        return baseDto;
    }
}
