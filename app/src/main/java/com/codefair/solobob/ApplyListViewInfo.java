package com.codefair.solobob;

public class ApplyListViewInfo {

    private Long id;
    private Long userId;
    private String userName;
    private Long scheduleId;
    private String status;
    private String explanation;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(Long scheduleId) {
        this.scheduleId = scheduleId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public ApplyListViewInfo(String explanation, ApplyInfoTO applyInfoTO) {
        this.id = applyInfoTO.getId();
        this.userName = applyInfoTO.getUserName();
        this.scheduleId = applyInfoTO.getScheduleId();
        this.status = applyInfoTO.getStatus();
        this.explanation = explanation;
    }
}
