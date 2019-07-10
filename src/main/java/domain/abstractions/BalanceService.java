package domain.abstractions;

import domain.dataTypes.AccountId;
import domain.dataTypes.Amount;
import domain.entities.Account;

import java.util.Optional;

public interface BalanceService {
    boolean verifyBalance(AccountId accountId,Amount amount);
    boolean addAccountBalance(AccountId accountId, Amount amount);
}
