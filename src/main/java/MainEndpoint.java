import com.google.gson.Gson;
import domain.abstractions.UserService;
import response.StandardResponse;
import response.StatusResponse;
import services.UserServiceImpl;

import static spark.Spark.get;

public class MainEndpoint {
    private static UserService userService;

    public static void main(String[] args) {
        buildServices();

        get("/hello", (req, res) -> "Hello, world");

        get("/hello/:name", (req, res) -> "Hello, " + req.params(":name"));
        get("/hello/:name", (req, res) -> "Hello, " + req.params(":name"));
        get("/users", (request, response) -> {
            response.type("application/json");

            return new Gson().toJson(
                    new StandardResponse(StatusResponse.SUCCESS, new Gson().toJsonTree(userService.getUsers())));
        });
    }

    private static void buildServices(){
        userService = new UserServiceImpl();
    }
}