package domain.dataTypes;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class TransactionId {
    private final String transactionId;

    private TransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public static TransactionId Of(String transactionId){
        return new TransactionId(transactionId);
    }

    public String value(){
        return transactionId;
    }
}
