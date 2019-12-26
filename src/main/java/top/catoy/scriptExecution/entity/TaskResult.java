package top.catoy.scriptExecution.entity;

/**
 * @ClassName TaskResult
 * @Description TODO
 * @Author admin
 * @Date 2019-12-12 18:16
 * @Version 1.0
 **/
public class TaskResult {
    private Object resultData;// 方法执行返回的对象
    private StringBuffer resultPrint; // 执行时的输出
    private Boolean isCompileSuccess; // 编译是否成功
    private Boolean isInvokeSuccess; // 反射执行方法是否成功
    private StringBuffer errorMsg; //编译和执行时的错误

    public TaskResult(Object resultData, StringBuffer resultPrint, Boolean isCompileSuccess, Boolean isInvokeSuccess, StringBuffer errorMsg) {
        this.resultData = resultData;
        this.resultPrint = resultPrint;
        this.isCompileSuccess = isCompileSuccess;
        this.isInvokeSuccess = isInvokeSuccess;
        this.errorMsg = errorMsg;
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

    @Override
    public String toString() {
        return "TaskResult{" +
                "resultData=" + resultData +
                ", resultPrint=" + resultPrint +
                '}';
    }
}
