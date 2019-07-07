package services;

import domain.abstractions.TransactionService;
import domain.dataTypes.AccountId;
import domain.dataTypes.TransactionId;
import domain.entities.Transaction;
import domain.entities.TransactionData;

import java.util.List;
import java.util.Optional;

public class TransactionServiceImpl implements TransactionService {
    @Override
    public Optional<Transaction> get(TransactionId transactionId) {
        return null;
    }

    @Override
    public Transaction create(TransactionData transactionData) {
        return null;
    }

    @Override
    public List<Transaction> getAccountTransactions(AccountId accountId, int count) {
        return null;
    }
}
