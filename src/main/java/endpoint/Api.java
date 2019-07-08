package endpoint;

import com.google.gson.Gson;
import di.Context;
import domain.dataTypes.*;
import domain.entities.AccountData;
import domain.entities.TransactionData;
import endpoint.exceptions.ApiError;
import endpoint.json.AccountDataJson;
import endpoint.json.TransactionDataJson;

import java.util.HashMap;
import java.util.Map;

import static spark.Spark.*;

public class Api {

    private static Context context = Context.New();

    public static void main(String[] args) {

        Gson gson = new Gson();
        try {


            get("/TestConnection", (req, res) -> "Up and running");

            get("/Account/:id", (req, res) -> context.accountController().getAccount(req.params(":id")).toJson());

            post("/Account/:id", (req, res) ->
            {
                try {
                    AccountDataJson accountDataJson = gson.fromJson(req.body(), AccountDataJson.class);
                    return context.accountController().updateAccount(req.params(":id"), fromJson(accountDataJson)).toJson();
                } catch (Exception ex) {
                    throw new ApiError(500, ex.getMessage());
                }
            });

            put("/Account", (req, res) -> {
                try {
                    AccountDataJson accountDataJson = gson.fromJson(req.body(), AccountDataJson.class);
                    return context.accountController().createAccount(fromJson(accountDataJson)).toJson();
                } catch (Exception ex) {
                    throw new ApiError(500, ex.getMessage());
                }
            });

            delete("/Account/:id", (req, res) -> context.accountController().deleteAccount(req.params(":id")).toJson());

            get("/Account/Transactions/:accountId/:count", (req, res) -> context.transactionsController().getAccountTransactions(req.params(":accountId"), req.params(":count")));

            get("/Transaction/:id", (req, res) -> context.transactionsController().getTransaction(req.params(":id")));

            post("/Transaction", (req, res) -> {
                TransactionDataJson transactionData = gson.fromJson(req.body(), TransactionDataJson.class);
                return context.transactionsController().createTransaction(fromJson(transactionData)).toJson();
            });
        } catch (Exception ex) {
            throw new ApiError(500, ex.getMessage());
        }
        exception(ApiError.class, (exc, req, res) -> {
            ApiError err = exc;
            Map<String, Object> jsonMap = new HashMap<>();
            jsonMap.put("status", err.getStatus());
            jsonMap.put("errorMessage", err.getMessage());
            res.type("application/json");
            res.status(err.getStatus());
            res.body(gson.toJson(jsonMap));
        });

        after((req, res) -> {
            res.type("application/json");
        });
    }

    private static AccountData fromJson(AccountDataJson accountDataJson) {
        return new AccountData(Name.Of(accountDataJson.getName()), LastName.Of(accountDataJson.getLastName()), Address.Of(accountDataJson.getAddress()), Currency.Of(accountDataJson.getCurrency()));
    }

    private static TransactionData fromJson(TransactionDataJson transactionDataJson) {
        return new TransactionData(AccountId.Of(transactionDataJson.getSender()), AccountId.Of(transactionDataJson.getReceiver()), Amount.Of(transactionDataJson.getAmount().getValue(), Currency.Of(transactionDataJson.getAmount().getCurrency())));
    }
}