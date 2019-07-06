package domain.entities;

import domain.dataTypes.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@AllArgsConstructor
@EqualsAndHashCode
@Getter
public class Account {

    private final AccountId accountId;
    private final Name name;
    private final LastName lastName;
    private final Address address;
    private final Amount balance;

}
