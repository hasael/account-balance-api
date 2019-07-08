package integration;

import com.google.gson.Gson;
import endpoint.Api;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import spark.Spark;

import static org.junit.Assert.assertNotNull;

public class IntegrationTests {

    public static final String PORT = "4567";
    private ApiClient client;
    private Gson gson;

    @BeforeClass
    public static void startServer() {
        String[] args = {PORT};
        Api.main(args);
    }

    @AfterClass
    public static void stopServer() {
        Spark.stop();
    }

    @Before
    public void setUp() {
        client = new ApiClient("http://localhost:" + PORT);
        gson = new Gson();
    }

    @Test
    public void testConnection() {

        ApiResponse res = client.request("GET",
                "/TestConnection");

        String actual = res.getBody();

        assertNotNull(actual);

    }


}
