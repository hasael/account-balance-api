package domain.dataTypes;

public class AccountId {

    private final String accountId;

    private AccountId(String accountId) {
        this.accountId = accountId;
    }

    public static AccountId Of(String accountId){
        return new AccountId(accountId);
    }
}
