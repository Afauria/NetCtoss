package com.zwy.work.entity;

import java.sql.Timestamp;
import java.util.List;

public class Admin {
    private Integer adminId;
    private String adminCode;
    private String password;
    private String adminName;
    private String telephone;
    private String email;
    private Timestamp enrolldate;
    private List<Role> adminRoles;
    private List<Module> adminModules;
    public Admin() {
    }

    public Admin(Integer adminId, String adminCode, String password, String adminName, String telephone, String email, Timestamp enrolldate, List<Role> adminRoles, List<Module> adminModules) {
        this.adminId = adminId;
        this.adminCode = adminCode;
        this.password = password;
        this.adminName = adminName;
        this.telephone = telephone;
        this.email = email;
        this.enrolldate = enrolldate;
        this.adminRoles = adminRoles;
        this.adminModules = adminModules;
    }

    public List<Module> getAdminModules() {
        return adminModules;
    }

    public void setAdminModules(List<Module> adminModules) {
        this.adminModules = adminModules;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Integer getAdminId() {
        return adminId;
    }

    public void setAdminId(Integer adminId) {
        this.adminId = adminId;
    }

    public String getAdminCode() {
        return adminCode;
    }

    public void setAdminCode(String adminCode) {
        this.adminCode = adminCode;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Timestamp getEnrolldate() {
        return enrolldate;
    }

    public void setEnrolldate(Timestamp enrolldate) {
        this.enrolldate = enrolldate;
    }

    public List<Role> getAdminRoles() {
        return adminRoles;
    }

    public void setAdminRoles(List<Role> adminRoles) {
        this.adminRoles = adminRoles;
    }
}
