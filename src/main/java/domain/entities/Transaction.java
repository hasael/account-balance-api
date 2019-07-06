package domain.entities;

import domain.dataTypes.AccountId;
import domain.dataTypes.Amount;
import domain.dataTypes.TransactionId;
import domain.dataTypes.TransactionTime;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class Transaction {

    private final TransactionId transactionId;
    private final AccountId sender;
    private final AccountId receiver;
    private final Amount amount;
    private final TransactionTime transactionTime;
}
