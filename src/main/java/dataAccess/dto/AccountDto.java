package dataAccess.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;


@Getter
@EqualsAndHashCode(callSuper = false)
public class AccountDto implements BaseDto {

    private final String name;
    private final String lastName;
    private final String address;
    private final AmountDto balance;


    public AccountDto(String name, String lastName, String address, AmountDto balance) {

        this.name = name;
        this.lastName = lastName;
        this.address = address;
        this.balance = balance;
    }

    public AccountDto withBalance(AmountDto amountDto) {
        return new AccountDto(getName(), getLastName(), getAddress(), amountDto);
    }
}
