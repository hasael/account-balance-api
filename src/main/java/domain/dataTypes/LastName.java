package domain.dataTypes;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class LastName {
    private final String lastName;

    private LastName(String lastName) {
        this.lastName = lastName;
    }

    public static LastName Of(String lastName) {
        return new LastName(lastName);
    }

    public String value(){
        return lastName;
    }
}
