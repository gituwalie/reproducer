package exception;

public enum StatusCodes {
    RMIAC_4000("RMIAC-4000", "Generic error");

    final String code;
    final String message;

    StatusCodes(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
