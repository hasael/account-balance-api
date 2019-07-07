package domain.dataTypes;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class Name {
    private final String name;

    private Name(String name) {
        this.name = name;
    }

    public static Name Of (String name){
        return new Name(name);
    }

    public String value(){
        return name;
    }
}
