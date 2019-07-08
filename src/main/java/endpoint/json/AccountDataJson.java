package endpoint.json;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@AllArgsConstructor
@EqualsAndHashCode
@Getter
public class AccountDataJson {
    private final String name;
    private final String lastName;
    private final String address;
    private final String currency;
}
