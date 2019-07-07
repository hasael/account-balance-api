package dataAccess.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Date;

@AllArgsConstructor
@Getter
@EqualsAndHashCode(callSuper = false)
public class TransactionDto extends BaseDto {

    private final UUID sender;
    private final UUID receiver;
    private final AmountDto amountDto;
    private final Date transactionTime;
}
