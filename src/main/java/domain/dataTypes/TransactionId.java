package domain.dataTypes;

public class TransactionId {
    private final String transactionId;

    private TransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public static TransactionId Of(String transactionId){
        return new TransactionId(transactionId);
    }
}
