package domain.dataTypes;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class Amount {
    private final Double amount;
    private final Currency currency;

    private Amount(Double amount, Currency currency) {
        this.amount = amount;
        this.currency = currency;
    }

    public static Amount Of(Double amount, Currency currency){
        return new Amount(amount,currency);
    }

    public Double amountValue(){
        return amount;
    }

    public Currency currency(){
        return currency;
    }
}
