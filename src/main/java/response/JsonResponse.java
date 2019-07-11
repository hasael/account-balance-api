package response;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
@EqualsAndHashCode
public class JsonResponse {

    private final StatusResponse status;
    private final JsonElement data;

    private JsonResponse(StatusResponse status, JsonElement data) {
        this.status = status;
        this.data = data;
    }

    public static JsonResponse SuccessResponse(Object data) {
        return new JsonResponse(StatusResponse.SUCCESS, toJson(data));
    }

    public static JsonResponse InfoResponse(String message) {
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("message", message);
        return new JsonResponse(StatusResponse.SUCCESS, toJson(jsonMap));
    }

    public static JsonResponse ErrorResponse(Throwable throwable) {
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("message", throwable.getMessage());
        return new JsonResponse(StatusResponse.ERROR, toJson(jsonMap));
    }

    private static JsonElement toJson(Object obj) {
        return new Gson().toJsonTree(obj);
    }

    public JsonElement toJson() {
        return new Gson().toJsonTree(this);
    }
}