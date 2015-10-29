package com.tuotiansudai.api.dto;

import com.tuotiansudai.repository.model.RechargeModel;
import org.apache.commons.lang3.NotImplementedException;

import java.text.SimpleDateFormat;

public class RechargeDetailResponseDataDto extends BaseResponseDataDto {

    /**
     * 充值ID（定单号）
     */
    private String rechargeId;
    /**
     * 用户ID
     */
    private String userId;
    /**
     * 充值金额
     */
    private String actualMoney;
    /**
     * 充值类型（暂无数据）
     * 此功能暂未实现
     */
    private String rechargeType;
    /**
     * 充值状态
     */
    private String status;
    /**
     * 充值状态描述
     */
    private String statusDesc;
    /**
     * 充值时间
     */
    private String time;
    /**
     * 到账时间
     */
    private String successTime;

    public RechargeDetailResponseDataDto() {

    }

    public RechargeDetailResponseDataDto(RechargeModel recharge) {
        throw new NotImplementedException(getClass().getName());
    }

    public String getRechargeId() {
        return rechargeId;
    }

    public void setRechargeId(String rechargeId) {
        this.rechargeId = rechargeId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getActualMoney() {
        return actualMoney;
    }

    public void setActualMoney(String actualMoney) {
        this.actualMoney = actualMoney;
    }

    public String getRechargeType() {
        return rechargeType;
    }

    public void setRechargeType(String rechargeType) {
        this.rechargeType = rechargeType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusDesc() {
        return statusDesc;
    }

    public void setStatusDesc(String statusDesc) {
        this.statusDesc = statusDesc;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getSuccessTime() {
        return successTime;
    }

    public void setSuccessTime(String successTime) {
        this.successTime = successTime;
    }
}
