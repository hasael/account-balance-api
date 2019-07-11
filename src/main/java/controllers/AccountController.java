package controllers;

import domain.abstractions.AccountService;
import domain.abstractions.BalanceService;
import domain.dataTypes.AccountId;
import domain.dataTypes.Amount;
import domain.entities.Account;
import domain.entities.AccountData;
import domain.responses.Response;
import endpoint.json.AccountJson;
import endpoint.json.AmountJson;
import response.JsonResponse;

import java.util.Optional;

import static response.JsonResponse.*;

public class AccountController {
    private final AccountService accountService;
    private final BalanceService balanceService;


    public AccountController(AccountService accountService, BalanceService balanceService) {
        this.accountService = accountService;
        this.balanceService = balanceService;
    }

    public JsonResponse getAccount(String accountId) {
        Response<Account> data = accountService.get(AccountId.Of(accountId));

        return data.fold(success -> SuccessResponse(toJson(success.getValue())),
                errorResponse -> ErrorResponse(errorResponse.getThrowable()),
                notFound -> InfoResponse("Resource not found"));
    }

    public JsonResponse updateAccount(String accountId, AccountData accountData) {
        return accountService.update(AccountId.Of(accountId), accountData)
                .fold(accountSuccess -> SuccessResponse(toJson(accountSuccess.getValue())),
                        errorResponse -> ErrorResponse(errorResponse.getThrowable()),
                        notFound -> InfoResponse("Resource not found"));
    }

    public JsonResponse createAccount(AccountData accountData) {
        return accountService.create(accountData)
                .fold(accountSuccess -> SuccessResponse(toJson(accountSuccess.getValue())),
                        errorResponse -> ErrorResponse(errorResponse.getThrowable()),
                        notFound -> InfoResponse("Resource not found"));
    }

    public JsonResponse deleteAccount(String accountId) {
        return accountService.delete(AccountId.Of(accountId))
                .fold(accountSuccess -> SuccessResponse(toJson(accountSuccess.getValue())),
                        errorResponse -> ErrorResponse(errorResponse.getThrowable()),
                        notFound -> InfoResponse("Resource not found"));
    }

    public JsonResponse addBalance(String accountId, Amount amount) {
        return balanceService.addAccountBalance(AccountId.Of(accountId), amount)
                .fold(accountSuccess -> SuccessResponse(toJson(accountSuccess.getValue())),
                        errorResponse -> ErrorResponse(errorResponse.getThrowable()),
                        notFound -> InfoResponse("Resource not found"));
    }

    private static AccountJson toJson(Account account) {
        return new AccountJson(account.getAccountId().value(), account.getName().value(), account.getLastName().value(),
                account.getAddress().value(), new AmountJson(account.getBalance().amountValue(), account.getBalance().currency().value()));

    }
    private static AmountJson toJson(Amount amount) {
        return new AmountJson(amount.amountValue(),amount.currency().value());
    }
}
