package services;

import domain.abstractions.ExchangeService;
import domain.dataTypes.Amount;
import domain.dataTypes.Currency;

public class ExchangeServiceImpl implements ExchangeService {
    
    @Override
    public Amount exchangeAmount(Currency newCurrency, Amount amount) {
        double currenciesExchangeRate = 1; //For now will keep it simple
        double newAmount = amount.amountValue() * currenciesExchangeRate;
        return Amount.Of(newAmount, newCurrency);
    }
}
