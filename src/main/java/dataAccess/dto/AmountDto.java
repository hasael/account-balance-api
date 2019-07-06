package dataAccess.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@AllArgsConstructor
@EqualsAndHashCode
@Getter
public class AmountDto {
    private final double moneyAmount;
    private final String currency;
}
