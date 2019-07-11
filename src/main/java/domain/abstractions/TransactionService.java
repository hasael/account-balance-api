package domain.abstractions;

import domain.dataTypes.AccountId;
import domain.dataTypes.TransactionId;
import domain.entities.Transaction;
import domain.entities.TransactionData;
import domain.responses.Response;

import java.util.List;

public interface TransactionService {

    Response<Transaction> get(TransactionId transactionId);

    Response<Transaction> create(TransactionData transactionData);

    Response<List<Transaction>> getAccountTransactions(AccountId accountId, int count);
}
