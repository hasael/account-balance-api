package di;

import controllers.AccountController;
import controllers.TransactionsController;
import dataAccess.Dao;
import dataAccess.dto.AccountDto;
import dataAccess.dto.TransactionDto;
import domain.abstractions.AccountService;
import domain.abstractions.TransactionService;
import services.AccountServiceImpl;
import services.TransactionServiceImpl;

import java.util.HashMap;

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
        Dao<AccountDto> accountDao = new Dao<>(new HashMap<>());
        Dao<TransactionDto> transactionDao = new Dao<>(new HashMap<>());
        accountService = new AccountServiceImpl(accountDao);
        transactionService = new TransactionServiceImpl();
        accountController = new AccountController(accountService);
        transactionsController = new TransactionsController(transactionService);
    }

    public AccountController accountController() {
        return accountController;
    }

    public TransactionsController transactionsController() {
        return transactionsController;
    }

    public AccountService accountService() {
        return accountService;
    }

    public TransactionService transactionService() {
        return transactionService;
    }
}
