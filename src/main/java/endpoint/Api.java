package endpoint;

import com.google.gson.Gson;
import di.Context;
import domain.entities.AccountData;
import domain.entities.TransactionData;

import static spark.Spark.*;

public class Api {

    private static Context context = Context.New();

    public static void main(String[] args) {

        get("/TestConnection", (req, res) -> "Up and running");

        get("/Account/:id", (req, res) -> context.accountController().getAccount(req.params(":id")).toJson());

        post("Account/:id", (req, res) -> context.accountController().updateAccount(req.params(":id"), new Gson().fromJson(req.body(), AccountData.class)));

        put("Account", (req, res) -> context.accountController().createAccount(new Gson().fromJson(req.body(), AccountData.class)));

        delete("/Account/:id", (req, res) -> context.accountController().deleteAccount(req.params(":id")).toJson());

        get("/Account/Transactions/:accountId/:count", (req, res) -> context.transactionsController().getAccountTransactions(req.params(":accountId"), req.params(":count")));

        get("/Transaction/:id", (req, res) -> context.transactionsController().getTransaction(req.params(":id")));

        post("/Transaction", (req, res) -> context.transactionsController().createTransaction(new Gson().fromJson(req.body(), TransactionData.class)));

    }

}