package controllers;

import domain.abstractions.AccountService;
import domain.abstractions.BalanceService;
import domain.dataTypes.AccountId;
import domain.dataTypes.Amount;
import domain.entities.Account;
import domain.entities.AccountData;
import endpoint.json.AccountJson;
import endpoint.json.AmountJson;
import response.Response;

import java.util.Optional;

import static response.Response.InfoResponse;
import static response.Response.SuccessResponse;

public class AccountController {
    private final AccountService accountService;
    private final BalanceService balanceService;


    public AccountController(AccountService accountService, BalanceService balanceService) {
        this.accountService = accountService;
        this.balanceService = balanceService;
    }

    public Response getAccount(String accountId) {
        Optional<Account> data = accountService.get(AccountId.Of(accountId));

        return data.map(account -> SuccessResponse(toJson(account)))
                .orElseGet(() -> InfoResponse("Resource not found"));
    }

    public Response updateAccount(String accountId, AccountData accountData) {
        accountService.update(AccountId.Of(accountId), accountData);
        return SuccessResponse(accountId + "updated");
    }

    public Response createAccount(AccountData accountData) {
        return SuccessResponse(toJson(accountService.create(accountData)));
    }

    public Response deleteAccount(String accountId) {
        accountService.delete(AccountId.Of(accountId));
        return SuccessResponse(accountId + "deleted");
    }

    public Response addBalance(String accountId, Amount amount) {
        balanceService.addAccountBalance(AccountId.Of(accountId), amount);
        return SuccessResponse(accountId + "balance updated");
    }

    private static AccountJson toJson(Account account) {
        return new AccountJson(account.getAccountId().value(), account.getName().value(), account.getLastName().value(),
                account.getAddress().value(), new AmountJson(account.getBalance().amountValue(), account.getBalance().currency().value()));

    }

}
