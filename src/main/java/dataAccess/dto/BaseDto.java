package dataAccess.dto;


public abstract class BaseDto {

    private final UUID id;

    protected BaseDto(UUID id) {
        this.id = id;
    }

    public UUID getId(){
        return id;
    }
}
