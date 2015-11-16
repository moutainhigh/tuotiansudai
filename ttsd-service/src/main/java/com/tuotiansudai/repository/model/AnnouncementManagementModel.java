package com.tuotiansudai.repository.model;

import com.tuotiansudai.dto.AnnouncementManagementDto;

import java.io.Serializable;
import java.util.Date;

public class AnnouncementManagementModel implements Serializable {

    private long id;

    private String title;

    private String content;

    private String contentText;

    private boolean showOnHome;

    private Date createdTime = new Date();

    private Date updateTime = new Date();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isShowOnHome() {
        return showOnHome;
    }

    public void setShowOnHome(boolean showOnHome) {
        this.showOnHome = showOnHome;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public AnnouncementManagementModel(){

    }

    public AnnouncementManagementModel(AnnouncementManagementDto announcementManagementDto) {
        this.id = announcementManagementDto.getId();
        this.title = announcementManagementDto.getTitle();
        this.content = announcementManagementDto.getContent();
        this.contentText = announcementManagementDto.getContentText();
        this.showOnHome = announcementManagementDto.isShowOnHome();
    }

    public String getContentText() {
        return contentText;
    }

    public void setContentText(String contentText) {
        this.contentText = contentText;
    }
}
