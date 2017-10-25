package com.tuotiansudai.repository.model;

import java.io.Serializable;
import java.util.Date;

public class PayrollDetailModel implements Serializable {

    private long id;

    private long payrollId;

    private String userName;

    private String mobile;

    private long amount;

    private PayrollStatusType payrollStatusType;

    private Date createdTime = new Date();

    public PayrollDetailModel() {
    }

    public PayrollDetailModel(long payrollId, String userName, String mobile, long amount, PayrollStatusType payrollStatusType, Date createdTime) {
        this.payrollId = payrollId;
        this.userName = userName;
        this.mobile = mobile;
        this.amount = amount;
        this.payrollStatusType = payrollStatusType;
        this.createdTime = createdTime;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getPayrollId() {
        return payrollId;
    }

    public void setPayrollId(long payrollId) {
        this.payrollId = payrollId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public PayrollStatusType getPayrollStatusType() {
        return payrollStatusType;
    }

    public void setPayrollStatusType(PayrollStatusType payrollStatusType) {
        this.payrollStatusType = payrollStatusType;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }
}