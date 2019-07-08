package dataAccess.dto;

import domain.dataTypes.Amount;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@AllArgsConstructor
@EqualsAndHashCode
@Getter
public class AmountDto {
    private final double moneyAmount;
    private final String currency;

    public static AmountDto empty() {
        return new AmountDto(0, "");
    }

    public static AmountDto Of(double moneyAmount, String currency) {
        return new AmountDto(moneyAmount, currency);
    }

    public static AmountDto from(Amount amount) {
        return new AmountDto(amount.amountValue(), amount.currency().value());
    }

}
