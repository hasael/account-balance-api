package domain.abstractions;

import domain.dataTypes.AccountId;
import domain.entities.Account;
import domain.entities.AccountData;

public interface AccountService {

    Account get(AccountId accountId);
    void update(AccountData accountData);
    Account create(AccountData accountData);
    void delete(AccountId accountId);
}
