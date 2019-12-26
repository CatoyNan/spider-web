package top.catoy.scriptExecution.exception;

import top.catoy.scriptExecution.enums.TaskExceptionStatusEnum;

public class ExecutionException extends RuntimeException {
    private int code;
    private String message;

    public ExecutionException(int code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public ExecutionException(TaskExceptionStatusEnum resultStatusEnum) {
        this.code = resultStatusEnum.getCode();
        this.message = resultStatusEnum.getMessage();
    }

    public ExecutionException(String message, Throwable cause, int code) {
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
