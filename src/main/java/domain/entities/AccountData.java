package domain.entities;

import domain.dataTypes.Address;
import domain.dataTypes.Currency;
import domain.dataTypes.LastName;
import domain.dataTypes.Name;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@AllArgsConstructor
@EqualsAndHashCode
@Getter
public class AccountData {
    private final Name name;
    private final LastName lastName;
    private final Address address;
    private final Currency currency;
}
