package com.zwy.work.entity;

public class Module {
    private Integer moduleId;
    private String moduleName;

    public Module() {
    }

    public Module(Integer moduleId, String moduleName) {
        this.moduleId = moduleId;
        this.moduleName = moduleName;
    }

    public int getModuleId() {
        return moduleId;
    }

    public void setModuleId(Integer moduleId) {
        this.moduleId = moduleId;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }
}
