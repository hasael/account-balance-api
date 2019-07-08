package services;

import dataAccess.Dao;
import dataAccess.dto.AccountDto;
import dataAccess.dto.AmountDto;
import dataAccess.dto.UUID;
import domain.abstractions.AccountService;
import domain.dataTypes.*;
import domain.entities.Account;
import domain.entities.AccountData;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Optional;

public class AccountServiceImpl implements AccountService {

    private final Dao<AccountDto> accountDao;

    public AccountServiceImpl(Dao<AccountDto> accountDao) {
        this.accountDao = accountDao;
    }

    @Override
    public Optional<Account> get(AccountId accountId) {
        return accountDao.read(UUID.Of(accountId.value())).map(accountDto -> accountFromDto(accountId, accountDto));
    }

    @Override
    public Optional<Account> update(AccountId accountId, AccountData accountData) {
        UUID id = UUID.Of(accountId.value());
        return accountDao.read(id)
                .flatMap(accountDto -> accountDao.update(dtoFromAccountData(accountData, accountDto.getBalance()), id)
                        .map(accountDto1 -> accountFromDto(accountId, accountDto1)));
    }

    @Override
    public Account create(AccountData accountData) {
        Pair<UUID, AccountDto> created = accountDao.create(dtoFromAccountData(accountData, AmountDto.Of(1.0, accountData.getCurrency().value())));
        return accountFromDto(AccountId.Of(created.getLeft().value()), created.getRight());
    }

    @Override
    public Optional<Account> delete(AccountId accountId) {
        return accountDao.delete(UUID.Of(accountId.value())).map(accountDto -> accountFromDto(accountId, accountDto));
    }

    private Account accountFromDto(AccountId accountId, AccountDto accountDto) {
        return new Account(accountId,
                Name.Of(accountDto.getName()),
                LastName.Of(accountDto.getLastName()),
                Address.Of(accountDto.getAddress()),
                Amount.Of(accountDto.getBalance().getMoneyAmount(), Currency.Of(accountDto.getBalance().getCurrency())));
    }

    private AccountDto dtoFromAccountData(AccountData accountData, AmountDto amountDto) {
        return new AccountDto(accountData.getName().value(), accountData.getLastName().value(), accountData.getAddress().value(), AmountDto.Of(amountDto.getMoneyAmount(), accountData.getCurrency().value()));
    }
}
