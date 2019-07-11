package domain.abstractions;

import domain.dataTypes.AccountId;
import domain.dataTypes.Amount;
import domain.responses.Response;

public interface BalanceService {
    boolean verifyBalance(AccountId accountId,Amount amount);
    Response<Amount> addAccountBalance(AccountId accountId, Amount amount);
}
