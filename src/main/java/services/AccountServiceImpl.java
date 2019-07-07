package services;

import domain.abstractions.AccountService;
import domain.dataTypes.AccountId;
import domain.entities.Account;
import domain.entities.AccountData;

public class AccountServiceImpl implements AccountService {
    @Override
    public Account get(AccountId accountId) {
        return null;
    }

    @Override
    public void update(AccountId accountId, AccountData accountData) {

    }

    @Override
    public Account create(AccountData accountData) {
        return null;
    }

    @Override
    public void delete(AccountId accountId) {

    }
}
