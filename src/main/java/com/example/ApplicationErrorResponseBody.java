package exception;

public class ApplicationErrorResponseBody {
    private String message;
    private StatusCodes code;

    public ApplicationErrorResponseBody(String message, StatusCodes code) {
        this.message = message;
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public StatusCodes getCode() {
        return code;
    }

    public void setCode(StatusCodes code) {
        this.code = code;
    }
}
