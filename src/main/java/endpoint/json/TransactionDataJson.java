package endpoint.json;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@AllArgsConstructor
@EqualsAndHashCode
@Getter
public class TransactionDataJson {
    private final String sender;
    private final String receiver;
    private final AmountJson amount;
}
