package com.tuotiansudai.ask.repository.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class AnswerModel implements Serializable {

    private long id;

    private String loginName;

    private String mobile;

    private String fakeMobile;

    private long questionId;

    private String answer;

    private boolean bestAnswer;

    private Date adoptedTime;

    private List<String> favoredBy;

    private String approvedBy;

    private Date approvedTime;

    private String rejectedBy;

    private Date rejectedTime;

    private AnswerStatus status;

    private Date createdTime;

    public AnswerModel() {
    }

    public AnswerModel(String loginName, String mobile, String fakeMobile, long questionId, String answer) {
        this.loginName = loginName;
        this.mobile = mobile;
        this.fakeMobile = fakeMobile;
        this.questionId = questionId;
        this.answer = answer;
        this.status = AnswerStatus.UNAPPROVED;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getFakeMobile() {
        return fakeMobile;
    }

    public void setFakeMobile(String fakeMobile) {
        this.fakeMobile = fakeMobile;
    }

    public long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(long questionId) {
        this.questionId = questionId;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public boolean isBestAnswer() {
        return bestAnswer;
    }

    public void setBestAnswer(boolean bestAnswer) {
        this.bestAnswer = bestAnswer;
    }

    public Date getAdoptedTime() {
        return adoptedTime;
    }

    public void setAdoptedTime(Date adoptedTime) {
        this.adoptedTime = adoptedTime;
    }

    public List<String> getFavoredBy() {
        return favoredBy;
    }

    public void setFavoredBy(List<String> favoredBy) {
        this.favoredBy = favoredBy;
    }

    public String getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(String approvedBy) {
        this.approvedBy = approvedBy;
    }

    public Date getApprovedTime() {
        return approvedTime;
    }

    public void setApprovedTime(Date approvedTime) {
        this.approvedTime = approvedTime;
    }

    public String getRejectedBy() {
        return rejectedBy;
    }

    public void setRejectedBy(String rejectedBy) {
        this.rejectedBy = rejectedBy;
    }

    public Date getRejectedTime() {
        return rejectedTime;
    }

    public void setRejectedTime(Date rejectedTime) {
        this.rejectedTime = rejectedTime;
    }

    public AnswerStatus getStatus() {
        return status;
    }

    public void setStatus(AnswerStatus status) {
        this.status = status;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }
}
