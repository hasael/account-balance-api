package services;

import dataAccess.Dao;
import dataAccess.dto.AmountDto;
import dataAccess.dto.TransactionDto;
import dataAccess.dto.UUID;
import domain.abstractions.BalanceService;
import domain.abstractions.TimeProvider;
import domain.abstractions.TransactionService;
import domain.dataTypes.*;
import domain.entities.Transaction;
import domain.entities.TransactionData;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;
import java.util.Optional;

public class TransactionServiceImpl implements TransactionService {

    private final BalanceService balanceService;
    private final TimeProvider timeProvider;
    private final Dao<TransactionDto> transactionDao;

    public TransactionServiceImpl(Dao<TransactionDto> transactionDao, BalanceService balanceService, TimeProvider timeProvider) {
        this.balanceService = balanceService;
        this.timeProvider = timeProvider;
        this.transactionDao = transactionDao;
    }

    @Override
    public Optional<Transaction> get(TransactionId transactionId) {
        return transactionDao.read(UUID.Of(transactionId.value())).map(transactionDto -> transactionFromDto(transactionId, transactionDto));
    }

    @Override
    public Optional<Transaction> create(TransactionData transactionData) {

        if (balanceService.verifyBalance(transactionData.getSender(), transactionData.getAmount())) {
            Pair<UUID, TransactionDto> created = transactionDao.create(dtoFromTransaction(transactionData));
            Transaction transaction = transactionFromDto(TransactionId.Of(created.getLeft().value()), created.getRight());

            balanceService.addAccountBalance(transaction.getSender(), transaction.getAmount().withNegativeAmount())
                    .flatMap(any -> balanceService.addAccountBalance(transaction.getReceiver(), transaction.getAmount()));

            return Optional.of(transaction);
        } else
            return Optional.empty();
    }

    @Override
    public List<Transaction> getAccountTransactions(AccountId accountId, int count) {
        return null;
    }

    private Transaction transactionFromDto(TransactionId id, TransactionDto transactionDto) {
        return new Transaction(id,
                AccountId.Of(transactionDto.getSender().value()),
                AccountId.Of(transactionDto.getReceiver().value()),
                Amount.Of(transactionDto.getAmountDto().getMoneyAmount(), Currency.Of(transactionDto.getAmountDto().getCurrency())),
                TransactionTime.Of(timeProvider.now())
        );
    }

    private TransactionDto dtoFromTransaction(TransactionData transaction) {
        //TODO: valuate adding a dto creation class
        return new TransactionDto(
                UUID.Of(transaction.getSender().value()),
                UUID.Of(transaction.getReceiver().value()),
                AmountDto.Of(transaction.getAmount().amountValue(), transaction.getAmount().currency().value()), timeProvider.now());
    }
}
