package dataAccess.dto;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class UUID {

    private final String uuid;

    private UUID(String uuid) {
        this.uuid = uuid;
    }

    public static UUID Of(String uuid){
        return new UUID(uuid);
    }

    public String value(){
        return uuid;
    }

    //TODO: there should be no need of these
    public static UUID empty(){
        return  Of("");
    }
}
