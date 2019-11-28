package top.catoy.exception;

public class CompileException extends RuntimeException {
    private int code;
    private String message;

    public CompileException(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public CompileException(CompileExceptionStatusEnum resultStatusEnum) {
        this.code = resultStatusEnum.getCode();
        this.message = resultStatusEnum.getMessage();
    }
}
