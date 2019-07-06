import static spark.Spark.get;

public class MainEndpoint {

    private static Context context = Context.New();

    public static void main(String[] args) {

        get("/TestConnection", (req, res) -> "Up and running");

        get("/Account/:id", (req, res) -> context.accountController().getAccount(req.params(":id")).toJson());

        get("/Account/Transactions/:accountId/:count", (req, res) -> context.transactionsController().getAccountTransactions(req.params(":accountId"), req.params(":count")));

        get("/Transaction/:id", (req, res) -> context.transactionsController().getTransaction(req.params(":id")));
    }

}