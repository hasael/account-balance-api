package domain.dataTypes;

import java.util.Date;

public class TransactionTime {
    private final Date time;

    private TransactionTime(Date time) {
        this.time = time;
    }

    public static TransactionTime Of(Date time) {
        return new TransactionTime(time);
    }

    public Date value() {
        return time;
    }
}
