package com.tuotiansudai.api.controller;

import com.tuotiansudai.api.dto.BankCardReplaceRequestDto;
import com.tuotiansudai.api.dto.BankCardRequestDto;
import com.tuotiansudai.api.dto.BaseResponseDto;
import com.tuotiansudai.api.service.MobileAppBankCardService;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MobileAppBankCardController extends MobileAppBaseController {

    @Autowired
    private MobileAppBankCardService mobileAppBankCardService;//查询签约／绑卡结果

    /**
     * @param bankCardRequestDto 绑卡请求参数
     * @return BaseResponseDto
     * @function 绑卡
     */
    @RequestMapping(value = "/bankcard/bind", method = RequestMethod.POST)
    public BaseResponseDto bankCardBind(@RequestBody BankCardRequestDto bankCardRequestDto) {
        return mobileAppBankCardService.bindBankCard(bankCardRequestDto);
    }

    /**
     * @param requestDto 换卡请求参数
     * @return BaseResponseDto
     * @function 换卡
     */
    @RequestMapping(value = "/bankcard/replace", method = RequestMethod.POST)
    public BaseResponseDto bankCardReplace(@RequestBody BankCardReplaceRequestDto requestDto) {
        throw new NotImplementedException(getClass().getName());
    }

    /**
     * @param bankCardRequestDto
     * @return BaseResponseDto
     * @function 签约
     */
    @RequestMapping(value = "/bankcard/sign", method = RequestMethod.POST)
    public BaseResponseDto bankCardSign(@RequestBody BankCardRequestDto bankCardRequestDto) {
        return mobileAppBankCardService.openFastPay(bankCardRequestDto);
    }

    @RequestMapping(value = "/bankcard/query", method = RequestMethod.POST)
    public BaseResponseDto queryBindAndSginStatus(@RequestBody BankCardRequestDto bankCardRequestDto) {
        return mobileAppBankCardService.queryStatus(bankCardRequestDto);
    }
}
