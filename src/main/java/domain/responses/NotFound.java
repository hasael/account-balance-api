package domain.responses;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class NotFound extends Response {
    public static NotFound notFound(){
        return new NotFound();
    }

    private NotFound(){

    }
}
