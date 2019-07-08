package domain.abstractions;

import domain.dataTypes.AccountId;
import domain.dataTypes.TransactionId;
import domain.entities.Transaction;
import domain.entities.TransactionData;

import java.util.List;
import java.util.Optional;

public interface TransactionService {

    Optional<Transaction> get(TransactionId transactionId);

    Optional<Transaction> create(TransactionData transactionData);

    List<Transaction> getAccountTransactions(AccountId accountId, int count);
}
