package com.zwy.work.entity;

import java.sql.Timestamp;
import java.util.Date;

public class Account {
    private int accountId;
    private int recommenderId;
    private String loginName;
    private String loginPasswd;
    private String status;//状态:1-开通,2-暂停,3-删除
    private Timestamp createDate;
    private Timestamp pauseDate;
    private Timestamp closeDate;
    private String realName;
    private String idCard;
    private Date birthdate;
    private String gender;
    private String occupation;
    private String telephone;
    private String email;
    private String mailAddress;
    private String zipcode;
    private String QQ;
    private Account recommender;
    private Timestamp lastLoginTime;
    private String lastLoginHost;

    public Account(int accountId, int recommenderId, String loginName, String loginPasswd, String status, Timestamp createDate, Timestamp
            pauseDate, Timestamp closeDate, String realName, String idCard, Date birthdate, String gender, String occupation, String telephone,
                   String email, String mailAddress, String zipcode, String QQ, Account recommender, Timestamp lastLoginTime, String lastLoginHost) {
        this.accountId = accountId;
        this.recommenderId = recommenderId;
        this.loginName = loginName;
        this.loginPasswd = loginPasswd;
        this.status = status;
        this.createDate = createDate;
        this.pauseDate = pauseDate;
        this.closeDate = closeDate;
        this.realName = realName;
        this.idCard = idCard;
        this.birthdate = birthdate;
        this.gender = gender;
        this.occupation = occupation;
        this.telephone = telephone;
        this.email = email;
        this.mailAddress = mailAddress;
        this.zipcode = zipcode;
        this.QQ = QQ;
        this.recommender = recommender;
        this.lastLoginTime = lastLoginTime;
        this.lastLoginHost = lastLoginHost;
    }

    public Account() {
    }

    public Timestamp getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Timestamp lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public String getLastLoginHost() {
        return lastLoginHost;
    }

    public void setLastLoginHost(String lastLoginHost) {
        this.lastLoginHost = lastLoginHost;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public int getRecommenderId() {
        return recommenderId;
    }

    public void setRecommenderId(int recommenderId) {
        this.recommenderId = recommenderId;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getLoginPasswd() {
        return loginPasswd;
    }

    public void setLoginPasswd(String loginPasswd) {
        this.loginPasswd = loginPasswd;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Account getRecommender() {
        return recommender;
    }

    public void setRecommender(Account recommender) {
        this.recommender = recommender;
    }

    public String getMailAddress() {
        return mailAddress;
    }

    public void setMailAddress(String mailAddress) {
        this.mailAddress = mailAddress;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getQQ() {
        return QQ;
    }

    public void setQQ(String QQ) {
        this.QQ = QQ;
    }
}
