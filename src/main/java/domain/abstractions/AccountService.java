package domain.abstractions;

import domain.dataTypes.AccountId;
import domain.entities.Account;
import domain.entities.AccountData;

import java.util.Optional;

public interface AccountService {

    Optional<Account> get(AccountId accountId);

    Optional<Account> update(AccountId accountId, AccountData accountData);

    Account create(AccountData accountData);

    Optional<Account> delete(AccountId accountId);
}
