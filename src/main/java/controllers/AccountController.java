package controllers;

import domain.abstractions.AccountService;
import domain.dataTypes.AccountId;
import domain.entities.AccountData;
import response.Response;

import static response.Response.SuccessResponse;

public class AccountController {
    private final AccountService accountService;


    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    public Response getAccount(String accountId) {
        return SuccessResponse(accountService.get(AccountId.Of(accountId)));
    }

    public Response updateAccount(String accountId, AccountData accountData) {
        accountService.update(AccountId.Of(accountId), accountData);
        return SuccessResponse(accountId + "updated");
    }

    public Response createAccount(AccountData accountData) {
        return SuccessResponse(accountService.create(accountData));
    }

    public Response deleteAccount(String accountId) {
        accountService.delete(AccountId.Of(accountId));
        return SuccessResponse(accountId + "deleted");
    }

}
