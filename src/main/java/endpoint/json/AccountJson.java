package endpoint.json;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@AllArgsConstructor
@EqualsAndHashCode
@Getter
public class AccountJson {
    private final String id;
    private final String name;
    private final String lastName;
    private final String address;
    private final AmountJson balance;
}
