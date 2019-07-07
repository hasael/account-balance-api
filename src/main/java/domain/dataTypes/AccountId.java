package domain.dataTypes;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class AccountId {

    private final String accountId;

    private AccountId(String accountId) {
        this.accountId = accountId;
    }

    public static AccountId Of(String accountId){
        return new AccountId(accountId);
    }

    public String value(){
        return accountId;
    }
}
