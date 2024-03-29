package di;

import controllers.AccountController;
import controllers.TransactionsController;
import dataAccess.Dao;
import dataAccess.UIDGenerator;
import dataAccess.dto.AccountDto;
import dataAccess.dto.TransactionDto;
import domain.abstractions.*;
import services.*;

import java.util.HashMap;

public class Context {

    private AccountService accountService;
    private TransactionService transactionService;
    private AccountController accountController;
    private TransactionsController transactionsController;
    private Dao<AccountDto> accountDao;
    private Dao<TransactionDto> transactionDao;

    private void buildServices() {

    }

    public static Context New() {
        Context context = new Context();
        context.buildServices();
        return context;
    }

    private Context() {
        UIDGenerator uidGenerator = new UIDGeneratorImpl();
        ExchangeService exchangeService = new ExchangeServiceImpl();
        accountDao = new Dao<>(new HashMap<>(), uidGenerator);
        transactionDao = new Dao<>(new HashMap<>(), uidGenerator);
        BalanceService balanceService = new BalanceServiceImpl(accountDao, exchangeService);
        TimeProvider timeProvider = new TimeProviderImpl();
        accountService = new AccountServiceImpl(accountDao);
        transactionService = new TransactionServiceImpl(transactionDao, balanceService, timeProvider);
        accountController = new AccountController(accountService, balanceService);
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

    public Dao<AccountDto> accountDao(){
        return accountDao;
    }

    public Dao<TransactionDto> transactionDao(){
        return transactionDao;
    }
}
