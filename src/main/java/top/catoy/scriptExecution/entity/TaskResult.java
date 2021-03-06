package top.catoy.scriptExecution.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName TaskResult
 * @Description TODO
 * @Author admin
 * @Date 2019-12-12 18:16
 * @Version 1.0
 **/
public class TaskResult implements Serializable {

    private int id;
    private Object resultData;// 方法执行返回的对象
    private StringBuffer resultPrint; // 执行时的输出
    private Boolean isCompileSuccess; // 编译是否成功
    private Boolean isInvokeSuccess; // 反射执行方法是否成功
    private StringBuffer errorMsg; //编译和执行时的错误
    private int taskId;//任务ID
    private Date gmtCreate;//创建时间

    public TaskResult(Object resultData, StringBuffer resultPrint, Boolean isCompileSuccess, Boolean isInvokeSuccess, StringBuffer errorMsg) {
        this.resultData = resultData;
        this.resultPrint = resultPrint;
        this.isCompileSuccess = isCompileSuccess;
        this.isInvokeSuccess = isInvokeSuccess;
        this.errorMsg = errorMsg;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Object getResultData() {
        return resultData;
    }

    public void setResultData(Object resultData) {
        this.resultData = resultData;
    }

    public StringBuffer getResultPrint() {
        return resultPrint;
    }

    public void setResultPrint(StringBuffer resultPrint) {
        this.resultPrint = resultPrint;
    }

    public Boolean getCompileSuccess() {
        return isCompileSuccess;
    }

    public void setCompileSuccess(Boolean compileSuccess) {
        isCompileSuccess = compileSuccess;
    }

    public Boolean getInvokeSuccess() {
        return isInvokeSuccess;
    }

    public void setInvokeSuccess(Boolean invokeSuccess) {
        isInvokeSuccess = invokeSuccess;
    }

    public StringBuffer getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(StringBuffer errorMsg) {
        this.errorMsg = errorMsg;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }


    @Override
    public String toString() {
        return "TaskResult{" +
                "id=" + id +
                ", resultData=" + resultData +
                ", resultPrint=" + resultPrint +
                ", isCompileSuccess=" + isCompileSuccess +
                ", isInvokeSuccess=" + isInvokeSuccess +
                ", errorMsg=" + errorMsg +
                ", taskId=" + taskId +
                ", gmtCreate=" + gmtCreate +
                '}';
    }
}
