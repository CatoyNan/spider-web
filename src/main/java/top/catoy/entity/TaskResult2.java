package top.catoy.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName TaskResult
 * @Description TODO
 * @Author admin
 * @Date 2019-12-12 18:16
 * @Version 1.0
 **/
public class TaskResult2 implements Serializable {

    private int id;
    private String resultData;// 方法执行返回的对象
    private String resultPrint; // 执行时的输出
    private String isCompileSuccess; // 编译是否成功
    private String isInvokeSuccess; // 反射执行方法是否成功
    private String errorMsg; //编译和执行时的错误
    private String taskId;//任务ID
    private Date gmtCreate;//创建时间

    public TaskResult2(String resultData, String resultPrint, String isCompileSuccess, String isInvokeSuccess, String errorMsg) {
        this.resultData = resultData;
        this.resultPrint = resultPrint;
        this.isCompileSuccess = isCompileSuccess;
        this.isInvokeSuccess = isInvokeSuccess;
        this.errorMsg = errorMsg;
    }

    public TaskResult2() {
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

    public void setResultData(String resultData) {
        this.resultData = resultData;
    }

    public String getResultPrint() {
        return resultPrint;
    }

    public void setResultPrint(String resultPrint) {
        this.resultPrint = resultPrint;
    }

    public String getIsCompileSuccess() {
        return isCompileSuccess;
    }

    public void setIsCompileSuccess(String isCompileSuccess) {
        this.isCompileSuccess = isCompileSuccess;
    }

    public String getIsInvokeSuccess() {
        return isInvokeSuccess;
    }

    public void setIsInvokeSuccess(String isInvokeSuccess) {
        this.isInvokeSuccess = isInvokeSuccess;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
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
        return "TaskResult2{" +
                "id=" + id +
                ", resultData=" + resultData +
                ", resultPrint='" + resultPrint + '\'' +
                ", isCompileSuccess=" + isCompileSuccess +
                ", isInvokeSuccess=" + isInvokeSuccess +
                ", errorMsg='" + errorMsg + '\'' +
                ", taskId=" + taskId +
                ", gmtCreate=" + gmtCreate +
                '}';
    }
}
