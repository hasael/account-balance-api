package services;

import dataAccess.Dao;
import dataAccess.dto.AmountDto;
import dataAccess.dto.TransactionDto;
import dataAccess.dto.UUID;
import domain.abstractions.BalanceService;
import domain.abstractions.ExchangeService;
import domain.abstractions.TimeProvider;
import domain.abstractions.TransactionService;
import domain.dataTypes.*;
import domain.entities.Transaction;
import domain.entities.TransactionData;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public class TransactionServiceImpl implements TransactionService {

    private final ExchangeService exchangeService;
    private final BalanceService balanceService;
    private final TimeProvider timeProvider;
    private final Dao<TransactionDto> transactionDao;

    public TransactionServiceImpl(Dao<TransactionDto> transactionDao, ExchangeService exchangeService, BalanceService balanceService, TimeProvider timeProvider) {
        this.exchangeService = exchangeService;
        this.balanceService = balanceService;
        this.timeProvider = timeProvider;
        this.transactionDao = transactionDao;
    }

    @Override
    public Optional<Transaction> get(TransactionId transactionId) {
        return transactionDao.read(UUID.Of(transactionId.value())).map(transactionDto -> transactionFromDto(transactionDto));
    }

    @Override
    public Transaction create(TransactionData transactionData) {
        Transaction transaction = transactionFromDto(transactionDao.create(dtoFromTransaction(transactionData)));
        balanceService.updateAccountBalance(transaction.getSender(), transaction.getAmount().withNegativeAmount())
                .flatMap(any -> balanceService.updateAccountBalance(transaction.getReceiver(), transaction.getAmount()));

        return transaction;
    }

    @Override
    public List<Transaction> getAccountTransactions(AccountId accountId, int count) {
        return null;
    }

    private Transaction transactionFromDto(TransactionDto transactionDto) {
        return new Transaction(TransactionId.Of(transactionDto.getId().value()),
                AccountId.Of(transactionDto.getSender().value()),
                AccountId.Of(transactionDto.getReceiver().value()),
                Amount.Of(transactionDto.getAmountDto().getMoneyAmount(), Currency.Of(transactionDto.getAmountDto().getCurrency())),
                TransactionTime.Of(timeProvider.now())
        );
    }

    private TransactionDto dtoFromTransaction(TransactionData transaction) {
        //TODO: valuate adding a dto creation class
        return new TransactionDto(UUID.empty(),
                UUID.Of(transaction.getSender().value()),
                UUID.Of(transaction.getReceiver().value()),
                AmountDto.Of(transaction.getAmount().amountValue(), transaction.getAmount().currency().value()), new Date());
    }
}
