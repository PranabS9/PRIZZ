package exceptions;

/**
 * [EXCEPTIONS] Database Layer Exception.
 * Purpose: Thrown by DAOs when JDBC queries or updates encounter SQLExceptions.
 * Syllabus: Exception chaining and wrapping low-level system exceptions.
 */
public class DatabaseOperationException extends PrizzAppException {
    public DatabaseOperationException(String message) {
        super("PRIZZ_ERR_DB", message, null);
    }

    public DatabaseOperationException(String message, Throwable cause) {
        super("PRIZZ_ERR_DB", message, cause);
    }
}

