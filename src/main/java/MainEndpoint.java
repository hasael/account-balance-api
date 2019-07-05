import com.google.gson.Gson;
import response.StandardResponse;
import response.StatusResponse;
import services.UserServiceImpl;

import static spark.Spark.get;

public class MainEndpoint {
    public static void main(String[] args) {

        UserServiceImpl userService = new UserServiceImpl();
        get("/hello", (req, res) -> "Hello, world");

        get("/hello/:name", (req, res) -> "Hello, " + req.params(":name"));
        get("/users", (request, response) -> {
            response.type("application/json");

            return new Gson().toJson(
                    new StandardResponse(StatusResponse.SUCCESS, new Gson().toJsonTree(userService.getUsers())));
        });
    }
}