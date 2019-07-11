package controllers;

import domain.abstractions.TransactionService;
import domain.dataTypes.AccountId;
import domain.dataTypes.TransactionId;
import domain.entities.Transaction;
import domain.entities.TransactionData;
import endpoint.json.AmountJson;
import endpoint.json.TransactionJson;
import response.JsonResponse;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static response.JsonResponse.*;

public class TransactionsController {
    private final TransactionService transactionService;

    public TransactionsController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    public JsonResponse getAccountTransactions(String accountId, String count) {
        return transactionService.getAccountTransactions(AccountId.Of(accountId), Integer.parseInt(count))
                .fold(listSuccess -> {
                            List<TransactionJson> response = listSuccess.getValue().stream().map(transaction -> toJson(transaction)).collect(Collectors.toList());
                            return SuccessResponse(response);
                        }, errorResponse -> ErrorResponse(errorResponse.getThrowable()),
                        notFound -> InfoResponse("Resource not found"));
    }

    public JsonResponse getTransaction(String transactionId) {
        return transactionService.get(TransactionId.Of(transactionId))
                .fold(transactionSuccess -> SuccessResponse(toJson(transactionSuccess.getValue())),
                        errorResponse -> ErrorResponse(errorResponse.getThrowable()),
                        notFound -> InfoResponse("Resource not found"));
    }

    public JsonResponse createTransaction(TransactionData transactionData) {
        return transactionService.create(transactionData)
                .fold(transactionSuccess -> SuccessResponse(toJson(transactionSuccess.getValue())),
                        errorResponse -> ErrorResponse(errorResponse.getThrowable()),
                        notFound -> InfoResponse("Resource not found"));
    }

    private static TransactionJson toJson(Transaction transaction) {
        return new TransactionJson(transaction.getTransactionId().value(), transaction.getSender().value(), transaction.getReceiver().value(), new AmountJson(transaction.getAmount().amountValue(), transaction.getAmount().currency().value()));
    }
}
