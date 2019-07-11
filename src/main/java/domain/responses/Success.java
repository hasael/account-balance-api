package domain.responses;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode(callSuper = false)
@Getter
public class Success<T> extends Response<T> {
    private final T value;

    private Success(T value) {
        this.value = value;
    }

    public static <U> Success Of(U v){
        return new Success<>(v);
    }

}
