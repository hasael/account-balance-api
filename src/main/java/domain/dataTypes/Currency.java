package domain.dataTypes;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class Currency {
    private final String currency;

    private Currency(String currency) {
        this.currency = currency;
    }

    public static Currency Of(String currency){
        return new Currency(currency);
    }

    public String value(){
        return currency;
    }
}
