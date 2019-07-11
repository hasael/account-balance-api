package domain.responses;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode
@Getter
public class ErrorResponse extends Response {
    private final Throwable throwable;

    private ErrorResponse(Throwable throwable) {
        this.throwable = throwable;
    }

    public static ErrorResponse Of(Throwable throwable) {
        return new ErrorResponse(throwable);
    }

}
