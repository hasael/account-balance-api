package integration;

import com.google.gson.Gson;
import dataAccess.Dao;
import dataAccess.dto.AccountDto;
import dataAccess.dto.AmountDto;
import dataAccess.dto.TransactionDto;
import dataAccess.dto.UUID;
import di.Context;
import endpoint.Api;
import endpoint.json.AccountJson;
import endpoint.json.AmountJson;
import endpoint.json.TransactionDataJson;
import endpoint.json.TransactionJson;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import response.Response;
import response.StatusResponse;
import spark.Spark;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class IntegrationTests {

    public static final String PORT = "4567";
    private ApiClient client;
    private Gson gson;
    private static Context context;

    @BeforeClass
    public static void startServer() {
        String[] args = {PORT};
        Api.main(args);
        context = Api.context;
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
    public void getAccount() {
        String name = "name";
        String lastName = "lastName";
        String address = "address";
        AmountDto amountDto = AmountDto.Of(10, "EUR");
        Dao<AccountDto> accountDao = context.accountDao();
        Pair<UUID, AccountDto> result = accountDao.create(new AccountDto(name, lastName, address, amountDto));
        String id = result.getLeft().value();

        ApiResponse getRes = client.request("GET",
                "/Account/" + id);

        String rawGetResponse = getRes.getBody();
        Response getResponse = gson.fromJson(rawGetResponse, Response.class);
        AccountJson getAccountJson = gson.fromJson(getResponse.getData(), AccountJson.class);

        assertEquals(getResponse.getStatus(), StatusResponse.SUCCESS);
        assertEquals(name, getAccountJson.getName());
        assertEquals(lastName, getAccountJson.getLastName());
        assertEquals(address, getAccountJson.getAddress());
        assertEquals(amountDto.getCurrency(), getAccountJson.getBalance().getCurrency());
        assertEquals(amountDto.getMoneyAmount(), getAccountJson.getBalance().getValue(), 0.0001);
    }

    @Test
    public void createAccount() {
        String name = "name";
        String lastName = "lastName";
        String address = "address";
        AmountDto amountDto = AmountDto.Of(0, "EUR");
        Dao<AccountDto> accountDao = context.accountDao();

        String accountCreate = "{\n" +
                "    \"name\": \"" + name + "\",\n" +
                "    \"lastName\": \"" + lastName + "\",\n" +
                "    \"address\": \"" + address + "\",\n" +
                "    \"currency\": \"" + amountDto.getCurrency() + "\"\n" +
                "}";

        ApiResponse res = client.request("PUT",
                "/Account", accountCreate);

        String rawPutResponse = res.getBody();
        Response putResponse = gson.fromJson(rawPutResponse, Response.class);
        AccountJson accountJson = gson.fromJson(putResponse.getData(), AccountJson.class);

        String id = accountJson.getId();

        AccountDto result = accountDao.read(UUID.Of(id)).get();

        assertEquals(name, result.getName());
        assertEquals(lastName, result.getLastName());
        assertEquals(address, result.getAddress());
        assertEquals(amountDto.getCurrency(), result.getBalance().getCurrency());
        assertEquals(amountDto.getMoneyAmount(), result.getBalance().getMoneyAmount(), 0.00001);
    }

    @Test
    public void addAccountBalance() throws InterruptedException {
        String name = "name";
        String lastName = "lastName";
        String address = "address";
        AmountDto amountDto = AmountDto.Of(0, "EUR");
        Dao<AccountDto> accountDao = context.accountDao();

        String accountCreate = "{\n" +
                "    \"name\": \"" + name + "\",\n" +
                "    \"lastName\": \"" + lastName + "\",\n" +
                "    \"address\": \"" + address + "\",\n" +
                "    \"currency\": \"" + amountDto.getCurrency() + "\"\n" +
                "}";

        ApiResponse res = client.request("PUT",
                "/Account", accountCreate);

        String rawPutResponse = res.getBody();
        Response putResponse = gson.fromJson(rawPutResponse, Response.class);
        AccountJson accountJson = gson.fromJson(putResponse.getData(), AccountJson.class);

        String id = accountJson.getId();
        AmountJson newAmount = new AmountJson(15.0, "EUR");
        client.request("POST",
                "/AccountBalance/" + id, gson.toJson(newAmount));

        Thread.sleep(1000);
        AccountDto result = accountDao.read(UUID.Of(id)).get();

        assertEquals(name, result.getName());
        assertEquals(lastName, result.getLastName());
        assertEquals(address, result.getAddress());
        assertEquals(amountDto.getCurrency(), result.getBalance().getCurrency());
        assertEquals(newAmount.getValue(), result.getBalance().getMoneyAmount(), 0.00001);
    }

    @Test
    public void createTransaction() {
        String name = "name";
        String lastName = "lastName";
        String address = "address";
        AmountDto amountDto = AmountDto.Of(0, "EUR");
        Dao<AccountDto> accountDao = context.accountDao();
        Dao<TransactionDto> transactionDao = context.transactionDao();
        Double transactionAmount = 10.0;
        Double firstAccountAmount = 15.0;
        Double secondAccountAmount = 7.0;

        String accountCreate = "{\n" +
                "    \"name\": \"" + name + "\",\n" +
                "    \"lastName\": \"" + lastName + "\",\n" +
                "    \"address\": \"" + address + "\",\n" +
                "    \"currency\": \"" + amountDto.getCurrency() + "\"\n" +
                "}";

        ApiResponse res = client.request("PUT",
                "/Account", accountCreate);

        String rawPutResponse = res.getBody();
        Response putResponse = gson.fromJson(rawPutResponse, Response.class);
        AccountJson accountJson = gson.fromJson(putResponse.getData(), AccountJson.class);
        String id = accountJson.getId();


        String accountCreate2 = "{\n" +
                "    \"name\": \"" + name + "\",\n" +
                "    \"lastName\": \"" + lastName + "\",\n" +
                "    \"address\": \"" + address + "\",\n" +
                "    \"currency\": \"" + amountDto.getCurrency() + "\"\n" +
                "}";
        ApiResponse res2 = client.request("PUT",
                "/Account", accountCreate2);
        String rawPutResponse2 = res2.getBody();
        Response putResponse2 = gson.fromJson(rawPutResponse2, Response.class);
        AccountJson accountJson2 = gson.fromJson(putResponse2.getData(), AccountJson.class);
        String id2 = accountJson2.getId();

        client.request("POST",
                "/AccountBalance/" + id, gson.toJson(new AmountJson(firstAccountAmount, "EUR")));

        client.request("POST",
                "/AccountBalance/" + id2, gson.toJson(new AmountJson(secondAccountAmount, "EUR")));

        TransactionDataJson transactionData = new TransactionDataJson(id, id2, new AmountJson(transactionAmount, "EUR"));

        ApiResponse tranRes = client.request("POST", "/Transaction", gson.toJson(transactionData));
        Response transactionResponse = gson.fromJson(tranRes.getBody(), Response.class);
        TransactionJson transactionJson = gson.fromJson(transactionResponse.getData(), TransactionJson.class);

        String transactionId = transactionJson.getId();
        AccountDto firstAccount = accountDao.read(UUID.Of(id)).get();
        AccountDto secondAccount = accountDao.read(UUID.Of(id2)).get();
        TransactionDto transactionDto = transactionDao.read(UUID.Of(transactionId)).get();

        assertEquals(amountDto.getCurrency(), firstAccount.getBalance().getCurrency());
        assertEquals(firstAccountAmount - transactionAmount, firstAccount.getBalance().getMoneyAmount(), 0.00001);

        assertEquals(amountDto.getCurrency(), secondAccount.getBalance().getCurrency());
        assertEquals(secondAccountAmount + transactionAmount, secondAccount.getBalance().getMoneyAmount(), 0.00001);

        assertEquals(transactionDto.getSender().value(), id);
        assertEquals(transactionDto.getReceiver().value(), id2);
        assertEquals(transactionDto.getAmountDto().getMoneyAmount(), transactionAmount, 0.0001);

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
