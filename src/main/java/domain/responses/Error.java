package domain.responses;

public class Error extends Response {
    private final Throwable throwable;

    private Error(Throwable throwable) {
        this.throwable = throwable;
    }

    public static Error Of(Throwable throwable) {
        return new Error(throwable);
    }

    public Throwable getThrowable() {
        return throwable;
    }
}
