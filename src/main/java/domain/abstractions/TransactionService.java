package domain.abstractions;

import domain.dataTypes.AccountId;
import domain.dataTypes.TransactionId;
import domain.entities.Transaction;
import domain.entities.TransactionData;

import java.util.List;

public interface TransactionService {

    Transaction get(TransactionId transactionId);
    Transaction create(TransactionData transactionData);
    List<Transaction> getAccountTransactions(AccountId accountId, int count);
}
