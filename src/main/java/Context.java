import controllers.AccountController;
import controllers.TransactionsController;
import domain.abstractions.AccountService;
import domain.abstractions.TransactionService;

public class Context {

    private AccountService accountService;
    private TransactionService transactionService;
    private AccountController accountController;
    private TransactionsController transactionsController;

    private void buildServices() {

    }

    public static Context New() {
        Context context = new Context();
        context.buildServices();
        return context;
    }

    private Context() {

    }

    public AccountController accountController() {
        return accountController;
    }

    public TransactionsController transactionsController() {
        return transactionsController;
    }

}
