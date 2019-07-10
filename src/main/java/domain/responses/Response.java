package domain.responses;

import java.util.function.Function;

public abstract class Response {
    public <T> T fold(Function<Success, T> successFunction,
                      Function<Error, T> errorFunction,
                      Function<NotFound, T> notFoundFunction) {
        if (this instanceof Success)
            return successFunction.apply((Success) this);
        else if (this instanceof Error)
            return errorFunction.apply((Error) this);
        else if (this instanceof NotFound)
            return notFoundFunction.apply((NotFound) this);

        throw new IllegalArgumentException("Cannot find instance of " + this.getClass());
    }
}
