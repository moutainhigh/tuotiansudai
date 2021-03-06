package com.tuotiansudai.api.service.v1_0.impl;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.tuotiansudai.api.dto.v1_0.*;
import com.tuotiansudai.api.service.v1_0.MobileAppUserBillListService;
import com.tuotiansudai.api.util.CommonUtils;
import com.tuotiansudai.api.util.PageValidUtils;
import com.tuotiansudai.repository.mapper.UserBillMapper;
import com.tuotiansudai.repository.model.UserBillModel;
import com.tuotiansudai.repository.model.UserBillOperationType;
import com.tuotiansudai.util.AmountConverter;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MobileAppUserBillListServiceImpl implements MobileAppUserBillListService {
    static Logger logger = Logger.getLogger(MobileAppUserBillListServiceImpl.class);
    @Autowired
    private UserBillMapper userBillMapper;

    @Autowired
    private PageValidUtils pageValidUtils;

    private final static Map<UserBillCategory, List<UserBillOperationType>> OPERATION_TYPE = Maps.newHashMap(ImmutableMap.<UserBillCategory, List<UserBillOperationType>>builder()
            .put(UserBillCategory.INCOMING, Lists.newArrayList(UserBillOperationType.TI_BALANCE, UserBillOperationType.UNFREEZE))
            .put(UserBillCategory.EXPENSE, Lists.newArrayList(UserBillOperationType.TO_BALANCE, UserBillOperationType.FREEZE,UserBillOperationType.TO_FREEZE))
            .build());

    @Override
    public BaseResponseDto<UserBillDetailListResponseDataDto> queryUserBillList(UserBillDetailListRequestDto userBillDetailListRequestDto) {
        BaseResponseDto dto = new BaseResponseDto();
        String loginName = userBillDetailListRequestDto.getUserId();
        Integer index = userBillDetailListRequestDto.getIndex();
        if(index == null || index <= 0){
            index = 1;
        }
        Integer pageSize = pageValidUtils.validPageSizeLimit(userBillDetailListRequestDto.getPageSize());
        UserBillCategory userBillCategory = userBillDetailListRequestDto.getUserBillCategory();
        List<UserBillOperationType> userBillOperationTypes= userBillCategory != null ? OPERATION_TYPE.get(userBillCategory):Lists.newArrayList();

        List<UserBillModel> userBillModels = userBillMapper.findUserBills(Maps.newHashMap(ImmutableMap.<String, Object>builder()
                .put("loginName", loginName)
                .put("userBillOperationTypes",userBillOperationTypes)
                .put("indexPage", (index - 1) * pageSize)
                .put("pageSize", pageSize).build()));

        int count = userBillMapper.findUserBillsCount(Maps.newHashMap(ImmutableMap.<String, Object>builder()
                .put("loginName", loginName)
                .put("userBillOperationTypes",userBillOperationTypes)
                .build()));
        dto.setCode(ReturnMessage.SUCCESS.getCode());
        dto.setMessage(ReturnMessage.SUCCESS.getMsg());
        UserBillDetailListResponseDataDto userBillDetailListResponseDataDto = new UserBillDetailListResponseDataDto();
        userBillDetailListResponseDataDto.setIndex(index);
        userBillDetailListResponseDataDto.setPageSize(pageSize);
        userBillDetailListResponseDataDto.setUserBillList(convertUserBillRecordDto(userBillModels));
        userBillDetailListResponseDataDto.setTotalCount(count);
        dto.setData(userBillDetailListResponseDataDto);
        return dto;
    }

    private List<UserBillRecordResponseDataDto> convertUserBillRecordDto(List<UserBillModel> userBillList) {
        List<UserBillRecordResponseDataDto> userBillRecords = Lists.newArrayList();
        for (UserBillModel userBill : userBillList) {
            UserBillRecordResponseDataDto userBillRecordResponseDataDto = new UserBillRecordResponseDataDto();
            userBillRecordResponseDataDto.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(userBill.getCreatedTime()));
            userBillRecordResponseDataDto.setMoney(CommonUtils.convertRealMoneyByType(userBill.getAmount(), userBill.getOperationType()));
            userBillRecordResponseDataDto.setTypeInfo(userBill.getBusinessType());
            userBillRecordResponseDataDto.setTypeInfoDesc(userBill.getBusinessType().getDescription());
            userBillRecordResponseDataDto.setFrozenMoney(AmountConverter.convertCentToString(userBill.getFreeze()));
            userBillRecordResponseDataDto.setDetail(userBill.getOperationType().getDescription());
            userBillRecords.add(userBillRecordResponseDataDto);
        }
        return userBillRecords;
    }
}
