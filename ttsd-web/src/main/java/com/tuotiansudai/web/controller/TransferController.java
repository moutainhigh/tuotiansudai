package com.tuotiansudai.web.controller;

import com.tuotiansudai.dto.BaseDto;
import com.tuotiansudai.dto.BasePaginationDataDto;
import com.tuotiansudai.repository.model.TransferStatus;
import com.tuotiansudai.transfer.service.InvestTransferService;
import com.tuotiansudai.transfer.service.TransferService;
import com.tuotiansudai.spring.LoginUserInfo;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.constraints.Min;
import java.util.List;

@Controller
@RequestMapping(value = "/transferrer")
public class TransferController {

    static Logger logger = Logger.getLogger(TransferController.class);

    @Autowired
    private InvestTransferService investTransferService;
    @Autowired
    private TransferService transferService;

    @RequestMapping(value = "/transfer-application-list/{transferStatus}", method = RequestMethod.GET)
    public ModelAndView getTransferrerTransferApplicationList(@PathVariable TransferStatus transferStatus) {
        ModelAndView modelAndView = new ModelAndView("/transfer-record");
        modelAndView.addObject("transferStatus", transferStatus);
        return modelAndView;
    }

    @RequestMapping(value = "/transfer-application-list", method = RequestMethod.GET)
    public ModelAndView getTransferrerTransferApplicationList() {
        ModelAndView modelAndView = new ModelAndView("/transfer-record");
        return modelAndView;
    }

    @RequestMapping(value = "/transfer-application-list-data", method = RequestMethod.GET)
    @ResponseBody
    public BaseDto<BasePaginationDataDto> transferrerListData(@Min(value = 1) @RequestParam(name = "index", defaultValue = "1", required = false) int index,
                                                              @RequestParam(name = "status", required = false) List<TransferStatus> statusList) {
        int pageSize = 10;
        String loginName = LoginUserInfo.getLoginName();
        BasePaginationDataDto dataDto;
        if (CollectionUtils.isNotEmpty(statusList) && statusList.contains(TransferStatus.TRANSFERABLE)) {
            dataDto = transferService.generateTransferableInvest(loginName, index, pageSize);
        } else {
            dataDto = investTransferService.findWebTransferApplicationPaginationList(loginName, statusList, index, pageSize);
        }

        dataDto.setStatus(true);
        BaseDto<BasePaginationDataDto> dto = new BaseDto<>();
        dto.setData(dataDto);

        return dto;
    }

}
