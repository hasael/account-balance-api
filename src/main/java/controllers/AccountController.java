package controllers;

import domain.abstractions.AccountService;
import domain.dataTypes.AccountId;
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
 
}
