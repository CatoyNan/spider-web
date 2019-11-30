package top.catoy.compile.entity;

/**
 * @ClassName CompileResponse
 * @Description TODO
 * @Author admin
 * @Date 2019-11-28 18:31
 * @Version 1.0
 **/
public class CompileResponse {
    private Integer status;
    private String msg;
    private Object data;

    public CompileResponse(Integer status, String msg, Object data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
