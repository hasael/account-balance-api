package services;

import dataAccess.Dao;
import dataAccess.dto.AccountDto;
import dataAccess.dto.AmountDto;
import dataAccess.dto.UUID;
import domain.abstractions.BalanceService;
import domain.dataTypes.*;
import domain.entities.Account;

import java.util.Optional;

public class BalanceServiceImpl implements BalanceService {
    private final Dao<AccountDto> accountDao;

    public BalanceServiceImpl(Dao<AccountDto> accountDao) {
        this.accountDao = accountDao;
    }

    @Override
    public Optional<Account> updateAccountBalance(AccountId accountId, Amount amount) {
        UUID id = UUID.Of(accountId.value());
        return accountDao.read(id)
                .flatMap(accountDto ->
                        accountDao.update(
                                accountDto.withBalance(
                                        AmountDto.Of(amount.amountValue(), amount.currency().value())), id).map(accountDto1 -> accountFromDto(accountDto1)));

    }

    private Account accountFromDto(AccountDto accountDto) {
        return new Account(AccountId.Of(accountDto.getId().value()),
                Name.Of(accountDto.getName()),
                LastName.Of(accountDto.getLastName()),
                Address.Of(accountDto.getAddress()),
                Amount.Of(accountDto.getBalance().getMoneyAmount(), Currency.Of(accountDto.getBalance().getCurrency())));
    }
}
