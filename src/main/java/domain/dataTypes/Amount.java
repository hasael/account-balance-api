package domain.dataTypes;

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
}
