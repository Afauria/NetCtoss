package com.zwy.work.entity;

import java.sql.Timestamp;
import java.util.List;

public class Bill {
    private Integer billId;
    private Float billFee;
    private String billMonth;
    private String payMode;
    private String payStatus;
    private Integer accountId;
    private Account account;
    private List<Service> services;

    public Bill() {
    }

    public Bill(Integer billId, Float billFee, String billMonth, String payMode, String payStatus, Integer accountId, Account account, List<Service> services) {
        this.billId = billId;
        this.billFee = billFee;
        this.billMonth = billMonth;
        this.payMode = payMode;
        this.payStatus = payStatus;
        this.accountId = accountId;
        this.account = account;
        this.services = services;
    }

    public List<Service> getServices() {
        return services;
    }

    public void setServices(List<Service> services) {
        this.services = services;
    }

    public Integer getBillId() {
        return billId;
    }

    public void setBillId(Integer billId) {
        this.billId = billId;
    }

    public void setBillFee(Float billFee) {
        this.billFee = billFee;
    }

    public Float getBillFee() {
        return billFee;
    }

    public String getBillMonth() {
        return billMonth;
    }

    public void setBillMonth(String billMonth) {
        this.billMonth = billMonth;
    }

    public String getPayMode() {
        return payMode;
    }

    public void setPayMode(String payMode) {
        this.payMode = payMode;
    }


    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public String getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(String payStatus) {
        this.payStatus = payStatus;
    }
}
