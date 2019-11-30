package top.catoy.exception;

import top.catoy.compile.enums.CompileExceptionStatusEnum;

public class CompileException extends RuntimeException {
    private int code;
    private String message;

    public CompileException(int code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public CompileException(CompileExceptionStatusEnum resultStatusEnum) {
        this.code = resultStatusEnum.getCode();
        this.message = resultStatusEnum.getMessage();
    }

    public CompileException(String message, Throwable cause, int code) {
        super(message,cause);
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
