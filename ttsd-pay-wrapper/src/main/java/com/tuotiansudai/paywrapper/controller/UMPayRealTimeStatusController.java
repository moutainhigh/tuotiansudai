package com.tuotiansudai.paywrapper.controller;

import com.google.common.collect.Maps;
import com.tuotiansudai.paywrapper.repository.model.sync.response.TransferSearchResponseModel;
import com.tuotiansudai.paywrapper.service.UMPayRealTimeStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
@RequestMapping(path = "/real-time")
public class UMPayRealTimeStatusController {

    @Autowired
    private UMPayRealTimeStatusService payRealTimeStatusService;

    @RequestMapping(path = "/user/{loginName}", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, String> getRealTimeUserStatus(@PathVariable String loginName) {
        return payRealTimeStatusService.getUserStatus(loginName);
    }

    @RequestMapping(path = "/user-balance/{loginName}", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, String> getRealTimeUserBalance(@PathVariable String loginName) {
        return payRealTimeStatusService.getUserBalance(loginName);
    }

    @RequestMapping(path = "/platform", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, String> getRealTimePlatformStatus() {
        return payRealTimeStatusService.getPlatformStatus();
    }

    @RequestMapping(path = "/loan/{loanId}", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, String> getRealTimeLoanStatus(@PathVariable long loanId) {
        return payRealTimeStatusService.getLoanStatus(loanId);
    }

    @RequestMapping(path = "/transfer/order-id/{orderId}/mer-date/{merDate}/business-type/{businessType}", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, String> getRealTimeTransferStatus(@PathVariable String orderId,
                                                         @PathVariable String merDate,
                                                         @PathVariable String businessType) {
        TransferSearchResponseModel transferStatus = payRealTimeStatusService.getTransferStatus(orderId, merDate, businessType);
        if (transferStatus != null && transferStatus.isSuccess()) {
            return transferStatus.generateHumanReadableInfo();
        }
        return Maps.newHashMap();
    }
}
