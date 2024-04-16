package exception;

public class ApplicationException extends RuntimeException {
    public Throwable exception;
    private final int status;
    ExceptionType type;

    public ApplicationException(Throwable exception, ExceptionType type, String message, int status) {
        super(message);
        this.exception = exception;
        this.type = type;
        this.status = status;
    }

    public ApplicationException(ExceptionType type, String message, int status) {
        super(message);
        this.type = type;
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public ExceptionType getType() {
        return type;
    }
}

