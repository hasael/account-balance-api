import domain.abstractions.AccountService;
import domain.abstractions.TransactionService;

public class Context {

    private AccountService accountService;
    private TransactionService transactionService;

    private void buildServices(){

    }

    public static Context New(){
        Context context = new Context();
        context.buildServices();
        return context;
    }

    private Context(){

    }

    public AccountService accountService(){
        return accountService;
    }
    public TransactionService transactionService(){
        return transactionService;
    }

}
