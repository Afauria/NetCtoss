package com.zwy.work.entity;

public class Bill {
    private Integer billId;
    private String owner;
    private String idCard;
    private String billAccount;
    private String billFee;
    private String billMonth;
    private String payMode;
    private Integer payStatus;

    public Bill() {
    }

    public Bill(Integer billId, String owner, String idCard, String billAccount, String billFee, String billMonth, String payMode, Integer payStatus) {
        this.billId = billId;
        this.owner = owner;
        this.idCard = idCard;
        this.billAccount = billAccount;
        this.billFee = billFee;
        this.billMonth = billMonth;
        this.payMode = payMode;
        this.payStatus = payStatus;
    }

    public Integer getBillId() {
        return billId;
    }

    public void setBillId(Integer billId) {
        this.billId = billId;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getBillAccount() {
        return billAccount;
    }

    public void setBillAccount(String billAccount) {
        this.billAccount = billAccount;
    }

    public String getBillFee() {
        return billFee;
    }

    public void setBillFee(String billFee) {
        this.billFee = billFee;
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

    public Integer getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(Integer payStatus) {
        this.payStatus = payStatus;
    }
}
