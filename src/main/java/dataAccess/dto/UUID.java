package dataAccess.dto;

public class UUID {

    private final String uuid;

    private UUID(String uuid) {
        this.uuid = uuid;
    }

    public static UUID Of(String uuid){
        return new UUID(uuid);
    }
}
