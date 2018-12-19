package com.tuotiansudai.service;

import cn.jpush.api.utils.StringUtils;
import com.tuotiansudai.dto.BaseDataDto;
import com.tuotiansudai.dto.BaseDto;
import com.tuotiansudai.dto.LoanApplicationDto;
import com.tuotiansudai.dto.LoanConsumeApplicationDto;
import com.tuotiansudai.repository.mapper.AccountMapper;
import com.tuotiansudai.repository.mapper.LoanApplicationMapper;
import com.tuotiansudai.repository.model.*;
import com.tuotiansudai.rest.client.mapper.UserMapper;
import com.tuotiansudai.util.IdentityNumberValidator;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoanApplicationService {

    private static Logger logger = Logger.getLogger(LoanApplicationService.class);

    @Autowired
    private AccountMapper accountMapper;

    @Autowired
    private LoanApplicationMapper loanApplicationMapper;

    @Autowired
    private UserMapper userMapper;

    public BaseDto<BaseDataDto> create(LoanApplicationDto loanApplicationDto) {
        BaseDataDto baseDataDto = checkLoanApplication(loanApplicationDto);
        if (baseDataDto.getStatus()){
            this.createLoanApplication(loanApplicationDto);
        }
        return new BaseDto<>(baseDataDto);
    }

    private BaseDataDto checkLoanApplication(LoanApplicationDto loanApplicationDto){
        if (null == accountMapper.findByLoginName(loanApplicationDto.getLoginName())) {
            return new BaseDataDto(false, "账户没有实名认证");
        }
        if (loanApplicationDto.getAmount() <= 0) {
            return new BaseDataDto(false, "借款金额必须是大于等于1的整数");
        }
        if (loanApplicationDto.getPeriod() <= 0) {
            return new BaseDataDto(false, "借款期限必须是大于等于1的整数");
        }
        if(StringUtils.isEmpty(loanApplicationDto.getLoanUsage())){
            return new BaseDataDto(false, "借款用途不能为空");
        }
        if(loanApplicationDto.getPledgeType() != PledgeType.NONE && StringUtils.isEmpty(loanApplicationDto.getPledgeInfo())){
            return new BaseDataDto(false, "抵押物信息不能为空");
        }
        return new BaseDataDto(true);
    }

    private LoanApplicationModel createLoanApplication(LoanApplicationDto loanApplicationDto){
        LoanApplicationModel loanApplicationModel = new LoanApplicationModel(loanApplicationDto);
        UserModel userModel = userMapper.findByLoginName(loanApplicationDto.getLoginName());
        loanApplicationModel.setMobile(userModel.getMobile());
        loanApplicationModel.setUserName(userModel.getUserName());
        loanApplicationModel.setIdentityNumber(userModel.getIdentityNumber());
        loanApplicationModel.setAge((short)IdentityNumberValidator.getAgeByIdentityCard(userModel.getIdentityNumber(),18));
        loanApplicationModel.setAddress(IdentityNumberValidator.getCityByIdentityCard(userModel.getIdentityNumber()));
        loanApplicationModel.setSex("MALE".equalsIgnoreCase(IdentityNumberValidator.getSexByIdentityCard(userModel.getIdentityNumber(),"MALE"))?"男":"女");
        loanApplicationMapper.create(loanApplicationModel);
        return loanApplicationModel;
    }

    public BaseDto<BaseDataDto> createConsume(LoanConsumeApplicationDto loanConsumeApplicationDto){
        BaseDataDto baseDataDto = this.checkLoanApplication(loanConsumeApplicationDto);
        if (!baseDataDto.getStatus()){
            return new BaseDto<>(baseDataDto);
        }
        if (StringUtils.isEmpty(loanConsumeApplicationDto.getIdentityProveUrls())) {
            return new BaseDto<>(new BaseDataDto(false, "身份证明材料不能为空"));
        }
        if (StringUtils.isEmpty(loanConsumeApplicationDto.getIncomeProveUrls())) {
            return new BaseDto<>(new BaseDataDto(false, "收入证明材料不能为空"));
        }
        if (StringUtils.isEmpty(loanConsumeApplicationDto.getCreditProveUrls())) {
            return new BaseDto<>(new BaseDataDto(false, "信用报告材料不能为空"));
        }
        if (loanConsumeApplicationDto.getMarriage() == Marriage.MARRIED && StringUtils.isEmpty(loanConsumeApplicationDto.getMarriageProveUrls())) {
            return new BaseDto<>(new BaseDataDto(false, "婚姻状况材料不能为空"));
        }
        if (StringUtils.isEmpty(loanConsumeApplicationDto.getPropertyProveUrls())) {
            return new BaseDto<>(new BaseDataDto(false, "资产证明材料不能为空"));
        }
        if (!StringUtils.isEmpty(loanConsumeApplicationDto.getTogetherLoaner()) && StringUtils.isEmpty(loanConsumeApplicationDto.getTogetherProveUrls())) {
            return new BaseDto<>(new BaseDataDto(false, "共同借款人材料不能为空"));
        }
        LoanApplicationModel loanApplicationModel = this.createLoanApplication(loanConsumeApplicationDto);
        loanApplicationModel.setMarriage(loanConsumeApplicationDto.getMarriage());
        LoanApplicationMaterialsModel loanApplicationMaterialsModel = new LoanApplicationMaterialsModel(loanApplicationModel.getId(), loanConsumeApplicationDto);
        loanApplicationMapper.createMaterials(loanApplicationMaterialsModel);
        return new BaseDto<>(new BaseDataDto(true));
    }
}
