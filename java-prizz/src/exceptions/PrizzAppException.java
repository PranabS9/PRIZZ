package exceptions;

/**
 * [EXCEPTIONS] Base Checked Application Exception.
 * Purpose: Root class for all domain-specific checked exceptions in PRIZZ.
 * Syllabus: Custom Exception creation, error codes, and exception propagation.
 */
public class PrizzAppException extends Exception {
    private final String errorCode;

    public PrizzAppException(String message) {
        super(message);
        this.errorCode = "PRIZZ_ERR_GENERIC";
    }

    public PrizzAppException(String message, Throwable cause) {
        super(message, cause);
        this.errorCode = "PRIZZ_ERR_CAUSE";
    }

    public PrizzAppException(String errorCode, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }

    @Override
    public String toString() {
        return String.format("[%s] %s", errorCode, getMessage());
    }
}

