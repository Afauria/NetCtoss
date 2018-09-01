package com.zwy.work.entity;

import java.util.List;

public class Role {
    private Integer roleId;
    private String roleName;
    private List<Module> roleModules;
    public Role() {
    }

    public Role(Integer roleId, String roleName, List<Module> roleModules) {
        this.roleId = roleId;
        this.roleName = roleName;
        this.roleModules = roleModules;
    }

    public List<Module> getRoleModules() {
        return roleModules;
    }

    public void setRoleModules(List<Module> roleModules) {
        this.roleModules = roleModules;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
