package domain.abstractions;

import domain.dataTypes.AccountId;
import domain.entities.Account;
import domain.entities.AccountData;
import domain.responses.Response;

import java.util.Optional;

public interface AccountService {

    Response<Account> get(AccountId accountId);

    Response<Account> update(AccountId accountId, AccountData accountData);

    Response<Account> create(AccountData accountData);

    Response<Account> delete(AccountId accountId);
}
