package dataAccess.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Date;

@Getter
@EqualsAndHashCode(callSuper = false)
public class TransactionDto implements BaseDto {

    private final UUID sender;
    private final UUID receiver;
    private final AmountDto amountDto;
    private final Date transactionTime;

    public TransactionDto(UUID sender, UUID receiver, AmountDto amountDto, Date transactionTime) {
        this.sender = sender;
        this.receiver = receiver;
        this.amountDto = amountDto;
        this.transactionTime = transactionTime;
    }
}
