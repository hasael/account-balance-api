package controllers;

import domain.abstractions.TransactionService;
import domain.dataTypes.AccountId;
import domain.dataTypes.TransactionId;
import response.Response;

import static response.Response.SuccessResponse;

public class TransactionsController {
    private final TransactionService transactionService;

    public TransactionsController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    public Response getAccountTransactions(String accountId, String count) {
        return SuccessResponse(transactionService.getAccountTransactions(AccountId.Of(accountId), Integer.parseInt(count)));
    }

    public Response getTransaction(String transactionId) {
        return SuccessResponse(transactionService.get(TransactionId.Of(transactionId)));
    }
}
