package domain.responses;

public class Success<T> extends Response {
    private final T value;

    private Success(T value) {
        this.value = value;
    }

    public static <U> Success Of(U v){
        return new Success<>(v);
    }

    public T getValue() {
        return value;
    }
}
