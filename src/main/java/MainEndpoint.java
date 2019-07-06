import static spark.Spark.get;

public class MainEndpoint {

    private static Context context = Context.New();

    public static void main(String[] args) {


        get("/TestConnection", (req, res) -> "Up and running");
    }


}