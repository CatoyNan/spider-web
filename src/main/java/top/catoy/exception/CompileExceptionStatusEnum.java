package top.catoy.exception;

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
    SOURCE_IS_NOT_VALID(1003, "source is not valid");

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
