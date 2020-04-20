package top.catoy.entity;

import java.util.Date;

/**
 * @ClassName Script
 * @Description TODO
 * @Author admin
 * @Date 2020-04-14 18:29
 * @Version 1.0
 **/
public class Script {
    private int Id;
    private String scriptName;
    private int scriptStatus;
    private Date gmtCreate;
    private Date gmtModified;
    private String collectCount;
    private int permissionType;
    private String scriptContent;
    private String methodName;
    private String methodArgs;
    private String className;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getScriptName() {
        return scriptName;
    }

    public void setScriptName(String scriptName) {
        this.scriptName = scriptName;
    }

    public int getScriptStatus() {
        return scriptStatus;
    }

    public void setScriptStatus(int scriptStatus) {
        this.scriptStatus = scriptStatus;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    public String getCollectCount() {
        return collectCount;
    }

    public void setCollectCount(String collectCount) {
        this.collectCount = collectCount;
    }

    public int getPermissionType() {
        return permissionType;
    }

    public void setPermissionType(int permissionType) {
        this.permissionType = permissionType;
    }

    public String getScriptContent() {
        return scriptContent;
    }

    public void setScriptContent(String scriptContent) {
        this.scriptContent = scriptContent;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getMethodArgs() {
        return methodArgs;
    }

    public void setMethodArgs(String methodArgs) {
        this.methodArgs = methodArgs;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }
}
