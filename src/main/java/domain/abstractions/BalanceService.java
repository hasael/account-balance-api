package domain.abstractions;

import domain.dataTypes.AccountId;
import domain.dataTypes.Amount;
import domain.entities.Account;

import java.util.Optional;

public interface BalanceService {
    Optional<Account> updateAccountBalance(AccountId accountId, Amount amount);
    boolean verifyBalance(AccountId accountId,Amount amount);
}
