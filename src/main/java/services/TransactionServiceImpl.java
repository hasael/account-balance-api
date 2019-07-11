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
import domain.responses.Response;

import java.util.List;

import static domain.responses.NotFound.notFound;

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
    public Response<Transaction> get(TransactionId transactionId) {
        return transactionDao.read(UUID.Of(transactionId.value())).map(transactionDto -> transactionFromDto(transactionId, transactionDto));
    }

    @Override
    public Response<Transaction> create(TransactionData transactionData) {

        if (balanceService.verifyBalance(transactionData.getSender(), transactionData.getAmount())) {
            Response<Transaction> transactionResponse = transactionDao.create(dtoFromTransaction(transactionData))
                    .map(created -> transactionFromDto(TransactionId.Of(created.getLeft().value()), created.getRight()));

            return transactionResponse
                    .flatMap(transaction -> balanceService.addAccountBalance(transaction.getSender(), transaction.getAmount().withNegativeAmount())
                            .flatMap(amount -> balanceService.addAccountBalance(transaction.getReceiver(), transaction.getAmount())))
                    .flatMap(amount -> transactionResponse);

        } else
            return notFound();
    }

    @Override
    public Response<List<Transaction>> getAccountTransactions(AccountId accountId, int count) {
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
        return new TransactionDto(
                UUID.Of(transaction.getSender().value()),
                UUID.Of(transaction.getReceiver().value()),
                AmountDto.Of(transaction.getAmount().amountValue(), transaction.getAmount().currency().value()), timeProvider.now());
    }
}
