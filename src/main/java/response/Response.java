package response;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class Response {

    private final StatusResponse status;
    private final JsonElement data;

    private Response(StatusResponse status, JsonElement data) {
        this.status = status;
        this.data = data;
    }

    public static Response SuccessResponse(Object data) {
        return new Response(StatusResponse.SUCCESS, toJson(data));
    }

    private static JsonElement toJson(Object obj) {
        return new Gson().toJsonTree(obj);
    }

    public JsonElement toJson(){
        return new Gson().toJsonTree(this);
    }
}