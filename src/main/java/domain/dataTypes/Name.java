package domain.dataTypes;

public class Name {
    private final String name;

    private Name(String name) {
        this.name = name;
    }

    public static Name Of (String name){
        return new Name(name);
    }
}
