package integration;

import com.google.gson.Gson;
import endpoint.Api;
import endpoint.json.AccountJson;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import response.Response;
import response.StatusResponse;
import spark.Spark;

import static org.junit.Assert.*;

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

    @Test
    public void accountsTest() {

        String accountCreate = "{\n" +
                "    \"name\": \"john\",\n" +
                "    \"lastName\": \"wick\",\n" +
                "    \"address\": \"via Pontelungo, 1\",\n" +
                "    \"currency\": \"EUR\"\n" +
                "}";

        ApiResponse res = client.request("PUT",
                "/Account", accountCreate);

        String rawPutResponse = res.getBody();
        Response putResponse = gson.fromJson(rawPutResponse, Response.class);
        AccountJson accountJson = gson.fromJson(putResponse.getData(), AccountJson.class);

        assertEquals(putResponse.getStatus(), StatusResponse.SUCCESS);
        assertEquals("john", accountJson.getName());

        String id = accountJson.getId();

        ApiResponse getRes = client.request("GET",
                "/Account/" + id);

        String rawGetResponse = getRes.getBody();
        Response getResponse = gson.fromJson(rawGetResponse, Response.class);
        AccountJson getAccountJson = gson.fromJson(getResponse.getData(), AccountJson.class);

        assertEquals(getResponse.getStatus(), StatusResponse.SUCCESS);
        assertEquals("john", getAccountJson.getName());


        String accountUpdate = "{\n" +
                "    \"name\": \"john2\",\n" +
                "    \"lastName\": \"wick2\",\n" +
                "    \"address\": \"via Pontelungo, 12\",\n" +
                "    \"currency\": \"EUR\"\n" +
                "}";

        ApiResponse postRes = client.request("POST",
                "/Account/" + id, accountUpdate);

        String rawPostResponse = postRes.getBody();
        Response postResponse = gson.fromJson(rawPostResponse, Response.class);

        assertEquals(postResponse.getStatus(), StatusResponse.SUCCESS);

        ApiResponse getRes2 = client.request("GET",
                "/Account/" + id);

        String rawGetResponse2 = getRes2.getBody();
        Response getResponse2 = gson.fromJson(rawGetResponse2, Response.class);
        AccountJson getAccountJson2 = gson.fromJson(getResponse2.getData(), AccountJson.class);

        assertEquals(getResponse2.getStatus(), StatusResponse.SUCCESS);
        assertEquals("john2", getAccountJson2.getName());
    }


}
