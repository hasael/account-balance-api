package domain.dataTypes;

public class Address {
    private final String address;

    private Address(String address) {
        this.address = address;
    }

    public static Address Of(String address){
        return new Address(address);
    }
}
