package services;

import dataAccess.Dao;
import dataAccess.dto.AccountDto;
import dataAccess.dto.AmountDto;
import dataAccess.dto.UUID;
import domain.abstractions.BalanceService;
import domain.abstractions.ExchangeService;
import domain.dataTypes.*;
import domain.entities.Account;

import java.util.Optional;

public class BalanceServiceImpl implements BalanceService {
    private final Dao<AccountDto> accountDao;
    private final ExchangeService exchangeService;

    public BalanceServiceImpl(Dao<AccountDto> accountDao, ExchangeService exchangeService) {
        this.accountDao = accountDao;
        this.exchangeService = exchangeService;
    }

    @Override
    public Optional<Account> updateAccountBalance(AccountId accountId, Amount amount) {
        UUID id = UUID.Of(accountId.value());
        return accountDao.read(id)
                .flatMap(accountDto ->
                        accountDao.update(
                                accountDto.withBalance(
                                        calculateBalance(Currency.Of(accountDto.getBalance().getCurrency()), amount)), id).map(accountDto1 -> accountFromDto(accountId, accountDto1)));

    }

    private AmountDto calculateBalance(Currency newCurrency, Amount amount) {
        Amount newAmount = exchangeService.exchangeAmount(newCurrency, amount);
        return AmountDto.Of(newAmount.amountValue(), newAmount.currency().value());
    }

    private Account accountFromDto(AccountId accountId, AccountDto accountDto) {
        return new Account(accountId,
                Name.Of(accountDto.getName()),
                LastName.Of(accountDto.getLastName()),
                Address.Of(accountDto.getAddress()),
                Amount.Of(accountDto.getBalance().getMoneyAmount(), Currency.Of(accountDto.getBalance().getCurrency())));
    }
}
