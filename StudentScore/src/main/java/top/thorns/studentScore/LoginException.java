package top.thorns.studentScore;


/**
 * <pre>
 * - 业务的异常消息
 * </pre>
 */
public class LoginException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    /**
     * - 异常编号
     */
    private int code;

    /**
     * - 异常消息
     */
    private String message;

    public LoginException(int code) {
        super("code." + code);
        this.code = code;
    }

    public LoginException(int code, String message) {
        super(message);
        this.code = code;
    }

    public LoginException(int code, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super("code." + code, cause, enableSuppression, writableStackTrace);
        this.code = code;
    }

    public LoginException(int code, String message, Throwable cause, boolean enableSuppression,
                          boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.code = code;
    }

    public LoginException(int code, Throwable cause) {
        super("code." + code, cause);
        this.code = code;
    }

    public LoginException(int code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message == null ? super.getMessage() : message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String getLocalizedMessage() {
        return super.getLocalizedMessage();
    }

    @Override
    public synchronized Throwable getCause() {
        return super.getCause();
    }

    @Override
    public StackTraceElement[] getStackTrace() {
        return super.getStackTrace();
    }

}
