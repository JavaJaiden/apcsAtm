package mp.atm.util;

public class Result<T> {
    private final String message;
    private final T value;

    public Result(String message, T value) {
        this.message = message;
        this.value = value;
    }

    public String getMessage() {
        return message;
    }

    public T getValue() {
        return value;
    }
}


