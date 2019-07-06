package domain.dataTypes;

public class LastName {
    private final String lastName;

    private LastName(String lastName) {
        this.lastName = lastName;
    }

    public static LastName Of(String lastName) {
        return new LastName(lastName);
    }
}
