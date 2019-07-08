package controllers;

import domain.abstractions.TransactionService;
import domain.dataTypes.AccountId;
import domain.dataTypes.TransactionId;
import domain.entities.Transaction;
import domain.entities.TransactionData;
import endpoint.json.AmountJson;
import endpoint.json.TransactionJson;
import response.Response;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static response.Response.InfoResponse;
import static response.Response.SuccessResponse;

public class TransactionsController {
    private final TransactionService transactionService;

    public TransactionsController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    public Response getAccountTransactions(String accountId, String count) {
        List<Transaction> transactions = transactionService.getAccountTransactions(AccountId.Of(accountId), Integer.parseInt(count));
        List<TransactionJson> response = transactions.stream().map(transaction -> toJson(transaction)).collect(Collectors.toList());
        return SuccessResponse(response);
    }

    public Response getTransaction(String transactionId) {
        Optional<Transaction> data = transactionService.get(TransactionId.Of(transactionId));
        return data.map(transaction -> SuccessResponse(transaction))
                .orElseGet(() -> InfoResponse("Resource not found"));
    }

    public Response createTransaction(TransactionData transactionData) {
        Optional<Transaction> data = transactionService.create(transactionData);
        return data.map(transaction -> SuccessResponse(transaction))
                .orElseGet(() -> InfoResponse("Could not create resource"));
    }

    private static TransactionJson toJson(Transaction transaction) {
        return new TransactionJson(transaction.getTransactionId().value(), transaction.getSender().value(), transaction.getReceiver().value(), new AmountJson(transaction.getAmount().amountValue(), transaction.getAmount().currency().value()));
    }
}
