package com.zwy.work.entity;

import java.sql.Timestamp;
import java.util.List;

public class Service {
    private int serviceId;
    private int accountId;
    private int costId;
    private String unixHost;
    private String osUsername;
    private String loginPasswd;
    private String status;
    private Timestamp createDate;
    private Timestamp pauseDate;
    private Timestamp closeDate;
    private Account account;
    private Cost cost;
    private List<Record> records;
    public Service() {
    }

    public Service(int serviceId, int accountId, int costId, String unixHost, String osUsername, String loginPasswd, String status, Timestamp createDate, Timestamp pauseDate, Timestamp closeDate, Account account, Cost cost, List<Record> records) {
        this.serviceId = serviceId;
        this.accountId = accountId;
        this.costId = costId;
        this.unixHost = unixHost;
        this.osUsername = osUsername;
        this.loginPasswd = loginPasswd;
        this.status = status;
        this.createDate = createDate;
        this.pauseDate = pauseDate;
        this.closeDate = closeDate;
        this.account = account;
        this.cost = cost;
        this.records = records;
    }

    public List<Record> getRecords() {
        return records;
    }

    public void setRecords(List<Record> records) {
        this.records = records;
    }

    public int getServiceId() {
        return serviceId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public int getCostId() {
        return costId;
    }

    public void setCostId(int costId) {
        this.costId = costId;
    }

    public String getUnixHost() {
        return unixHost;
    }

    public void setUnixHost(String unixHost) {
        this.unixHost = unixHost;
    }

    public String getOsUsername() {
        return osUsername;
    }

    public void setOsUsername(String osUsername) {
        this.osUsername = osUsername;
    }

    public String getLoginPasswd() {
        return loginPasswd;
    }

    public void setLoginPasswd(String loginPasswd) {
        this.loginPasswd = loginPasswd;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public Timestamp getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    public Timestamp getPauseDate() {
        return pauseDate;
    }

    public void setPauseDate(Timestamp pauseDate) {
        this.pauseDate = pauseDate;
    }

    public Timestamp getCloseDate() {
        return closeDate;
    }

    public void setCloseDate(Timestamp closeDate) {
        this.closeDate = closeDate;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Cost getCost() {
        return cost;
    }

    public void setCost(Cost cost) {
        this.cost = cost;
    }
}
