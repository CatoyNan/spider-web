package top.catoy.entity;

import java.util.Date;

/**
 * @ClassName Task
 * @Description TODO
 * @Author admin
 * @Date 2020-04-16 00:17
 * @Version 1.0
 **/
public class Task {
    private int id;
    private int userId;
    private int scriptId;
    private int taskStatus;
    private int executionsTime;
    private String url;
    private String cronExpression;
    private String jobGroup;
    private String jobClassName;
    private int callBack;
    private Date gmtCreate;
    private Date gmtModified;
    private String jobName;
    private int recentStatus;

    public int getRecentStatus() {
        return recentStatus;
    }

    public void setRecentStatus(int recentStatus) {
        this.recentStatus = recentStatus;
    }

    public String getCronExpression() {
        return cronExpression;
    }

    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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

    public int getExecutionsTime() {
        return executionsTime;
    }

    public void setExecutionsTime(int executionsTime) {
        this.executionsTime = executionsTime;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getJobGroup() {
        return jobGroup;
    }

    public void setJobGroup(String jobGroup) {
        this.jobGroup = jobGroup;
    }

    public String getJobClassName() {
        return jobClassName;
    }

    public void setJobClassName(String jobClassName) {
        this.jobClassName = jobClassName;
    }

    public int getCallBack() {
        return callBack;
    }

    public void setCallBack(int callBack) {
        this.callBack = callBack;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }
}
