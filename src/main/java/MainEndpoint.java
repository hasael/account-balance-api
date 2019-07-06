import static spark.Spark.get;

public class MainEndpoint {

    public static void main(String[] args) {
        buildServices();

        get("/TestConnection", (req, res) -> "Up and running");
    }

    private static void buildServices(){

    }
}