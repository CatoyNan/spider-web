package top.catoy.scriptExecution.enums;

/**
 * @ClassName TaskResultStatusEnum
 * @Description TODO
 * @Author admin
 * @Date 2019-11-29 13:56
 * @Version 1.0
 **/
public enum TaskResultStatusEnum {
    /**
     * 成功
     */
    SUCCESS(2000, "success"),

    /**
     * 未知错误
     */
    UNKNOWN_ERROR(2001, "unknown error"),

    /**
     * 失败
     */
    FAIL(2002, "fail");

//    /**
//     * 编译成功
//     */
//    COMPILE_SUCCESS(2002, "scriptExecution success"),
//
//    /**
//     * 编译失败，源文件无法编译
//     */
//    COMPILE_ERROR(2003, "scriptExecution error"),
//
//    /**
//     * 方法执行失败，invoke异常
//     */
//    INVOKE_ERROR(2004, "invoke error");



    private int code;

    private String message;

    TaskResultStatusEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
