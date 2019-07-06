package domain.abstractions;

import domain.dataTypes.AccountId;
import domain.dataTypes.Amount;

public interface BalanceService {
    Amount UpdateAccountBalance(AccountId accountId,Amount amount);
}
