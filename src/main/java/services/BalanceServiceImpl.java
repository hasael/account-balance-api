package services;

import dataAccess.Dao;
import dataAccess.dto.AccountDto;
import dataAccess.dto.AmountDto;
import dataAccess.dto.UUID;
import domain.abstractions.BalanceService;
import domain.abstractions.ExchangeService;
import domain.dataTypes.AccountId;
import domain.dataTypes.Amount;
import domain.dataTypes.Currency;
import org.apache.commons.lang3.tuple.Pair;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

public class BalanceServiceImpl implements BalanceService {
    private final Dao<AccountDto> accountDao;
    private final ExchangeService exchangeService;
    private BlockingQueue<Pair<AccountId, Amount>> workingQueue;

    public BalanceServiceImpl(Dao<AccountDto> accountDao, ExchangeService exchangeService) {
        this.accountDao = accountDao;
        this.exchangeService = exchangeService;
        workingQueue = new LinkedBlockingQueue<>();
        startConsumer();
    }

    private void startConsumer() {
        final ExecutorService consumers = Executors.newSingleThreadExecutor();
        consumers.submit(() -> {
            while (true) {
                synchronized (workingQueue) {
                    if (!workingQueue.isEmpty()) {
                        Pair<AccountId, Amount> result = workingQueue.take();
                        updateAmount(result.getLeft(), result.getRight());
                    }
                }
            }
        });
    }

    @Override
    public boolean addAccountBalance(AccountId accountId, Amount amount) {
        try {

            workingQueue.put(Pair.of(accountId, amount));

        } catch (Exception e) {
            return false;
        }
        return true;
    }

    private void updateAmount(AccountId accountId, Amount amount) {
        UUID id = UUID.Of(accountId.value());
        accountDao.read(id)
                .flatMap(accountDto ->
                        accountDao.update(
                                accountDto.withBalance(
                                        add(accountDto.getBalance(), AmountDto.from(amount))), id));
    }

    @Override
    public boolean verifyBalance(AccountId accountId, Amount amount) {
        UUID id = UUID.Of(accountId.value());
        return accountDao.read(id).map(accountDto ->
                accountDto.getBalance().getMoneyAmount() >= calculateBalance(Currency.Of(accountDto.getBalance().getCurrency()), amount).getMoneyAmount())
                .orElse(false);
    }

    private AmountDto calculateBalance(Currency newCurrency, Amount amount) {
        Amount newAmount = exchangeService.exchangeAmount(newCurrency, amount);
        return AmountDto.Of(newAmount.amountValue(), newAmount.currency().value());
    }

    public AmountDto add(AmountDto first, AmountDto second) {
        Amount newAmount = exchangeService.exchangeAmount(Currency.Of(first.getCurrency()), Amount.Of(second.getMoneyAmount(), Currency.Of(second.getCurrency())));
        return new AmountDto(first.getMoneyAmount() + newAmount.amountValue(), first.getCurrency());
    }

}
