package services;

import dataAccess.UIDGenerator;
import dataAccess.dto.UUID;

public class UIDGeneratorImpl implements UIDGenerator {
    @Override
    public UUID generateId() {
        return UUID.Of(java.util.UUID.randomUUID().toString());
    }
}
