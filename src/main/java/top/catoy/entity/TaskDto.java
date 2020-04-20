package top.catoy.entity;

import java.util.Date;

/**
 * @ClassName TaskDto
 * @Description TODO
 * @Author admin
 * @Date 2020-04-16 03:22
 * @Version 1.0
 **/
public class TaskDto {
    private int id;
    private String url;
    private String scriptName;
    private int scriptId;
    private int callBack;
    private String cronExpression;
    private Date gmtCreate;
    private int taskStatus;
    private String jobGroup;
    private int recentStatus;

    public int getRecentStatus() {
         return recentStatus;
    }

    public void setRecentStatus(int recentStatus) {
        this.recentStatus = recentStatus;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getScriptName() {
        return scriptName;
    }

    public void setScriptName(String scriptName) {
        this.scriptName = scriptName;
    }

    public int getScriptId() {
        return scriptId;
    }

    public void setScriptId(int scriptId) {
        this.scriptId = scriptId;
    }

    public int getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(int taskStatus) {
        this.taskStatus = taskStatus;
    }

    public String getJobGroup() {
        return jobGroup;
    }

    public void setJobGroup(String jobGroup) {
        this.jobGroup = jobGroup;
    }

    public int getCallBack() {
        return callBack;
    }

    public void setCallBack(int callBack) {
        this.callBack = callBack;
    }

    public String getCronExpression() {
        return cronExpression;
    }

    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }
}
