package com.zwy.work.entity;

import java.sql.Timestamp;

public class Record {
    private Integer recordId;
    private Integer serviceId;
    private Timestamp loginTime;
    private Timestamp logoutTime;
    private Integer duration;
    private float fee;

    public Record() {
    }

    public Record(Integer recordId, Integer serviceId, Timestamp loginTime, Timestamp logoutTime, Integer duration, float fee) {
        this.recordId = recordId;
        this.serviceId = serviceId;
        this.loginTime = loginTime;
        this.logoutTime = logoutTime;
        this.duration = duration;
        this.fee = fee;
    }

    public Integer getRecordId() {
        return recordId;
    }

    public void setRecordId(Integer recordId) {
        this.recordId = recordId;
    }

    public Integer getServiceId() {
        return serviceId;
    }

    public void setServiceId(Integer serviceId) {
        this.serviceId = serviceId;
    }

    public Timestamp getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Timestamp loginTime) {
        this.loginTime = loginTime;
    }

    public Timestamp getLogoutTime() {
        return logoutTime;
    }

    public void setLogoutTime(Timestamp logoutTime) {
        this.logoutTime = logoutTime;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public float getFee() {
        return fee;
    }

    public void setFee(float fee) {
        this.fee = fee;
    }
}
