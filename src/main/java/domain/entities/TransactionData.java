package domain.entities;

import domain.dataTypes.AccountId;
import domain.dataTypes.Amount;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@AllArgsConstructor
@EqualsAndHashCode
@Getter
public class TransactionData {
    private final AccountId sender;
    private final AccountId receiver;
    private final Amount amount;
}
