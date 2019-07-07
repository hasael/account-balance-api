package domain.dataTypes;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class Address {
    private final String address;

    private Address(String address) {
        this.address = address;
    }

    public static Address Of(String address){
        return new Address(address);
    }

    public String value(){
        return address;
    }
}
