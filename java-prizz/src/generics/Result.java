package generics;

/**
 * [GENERICS] Generic Response Envelope.
 * Purpose: Encapsulates operation status (success/failure), messages, and generic payload T.
 * Syllabus: Introductory Generics in class and generic static methods.
 *
 * @param <T> Payload data type
 */
public class Result<T> {
    private final boolean success;
    private final String message;
    private final T data;

    public Result(boolean success, String message, T data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    public static <T> Result<T> success(String message, T data) {
        return new Result<>(true, message, data);
    }

    public static <T> Result<T> failure(String message) {
        return new Result<>(false, message, null);
    }

    public boolean isSuccess() { return success; }
    public String getMessage() { return message; }
    public T getData() { return data; }

    @Override
    public String toString() {
        return String.format("Result[success=%b, message='%s', data=%s]", success, message, data);
    }
}

