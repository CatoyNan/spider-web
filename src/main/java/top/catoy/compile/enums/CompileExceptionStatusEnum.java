package top.catoy.compile.enums;

/**
 * @ClassName CompileExceptionStatusEnum
 * @Description TODO
 * @Author admin
 * @Date 2019-11-27 13:01
 * @Version 1.0
 **/
public enum CompileExceptionStatusEnum {
    /**
     * 文件路径无效
     */
    FILEPATH_NOT_VALID(1001, "filePath is not valid"),

    /**
     * 类名无效
     */
    CLASSNAME_IS_NOT_VALID(1002, "className is not valid"),

    /**
     * 编译源文件无效
     */
    SOURCE_IS_NOT_VALID(1003, "source is not valid"),

    /**
     * 编译出错
     */
    SOURCE_COMPILE_ERROR(1004, "source compile error"),

    /**
     * 反射方法参数出错
     */
    INVOKE_ARGS_ERROR(1005, "invoke args error"),

    /**
     * 反射调用方法内部异常
     */
    INVOKE_Target_Exception(1006,"invoke target exception"),

    /**
     * 编译的类无法加载
     */
    CLASS_CAN_NOT_LOAD(1007,"class can not load");


    private int code;

    private String message;

    CompileExceptionStatusEnum(int code, String message) {
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
