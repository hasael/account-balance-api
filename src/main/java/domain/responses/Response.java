package domain.responses;

import java.util.function.Function;

public abstract class Response<T> {
    public <R> R fold(Function<Success<T>, R> successFunction,
                      Function<ErrorResponse, R> errorFunction,
                      Function<NotFound, R> notFoundFunction) {
        if (this instanceof Success)
            return successFunction.apply((Success<T>) this);
        else if (this instanceof ErrorResponse)
            return errorFunction.apply((ErrorResponse) this);
        else if (this instanceof NotFound)
            return notFoundFunction.apply((NotFound) this);

        throw new IllegalArgumentException("Cannot find instance of " + this.getClass());
    }

    public <U> Response<U> map(Function<T, U> function) {
        return this.fold(responseSuccess -> Success.Of(function.apply(responseSuccess.getValue())), errorResponse -> errorResponse,
                notFound -> notFound);
    }

    public <U> Response<U> flatMap(Function<T, Response<U>> function) {
        return this.fold(responseSuccess -> function.apply(responseSuccess.getValue()),
                errorResponse -> errorResponse,
                notFound -> notFound);
    }
}
