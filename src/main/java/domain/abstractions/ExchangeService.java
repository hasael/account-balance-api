package domain.abstractions;

import domain.dataTypes.Amount;
import domain.dataTypes.Currency;

public interface ExchangeService {
    Amount exchangeAmount(Currency newCurrency, Amount amount);
}
