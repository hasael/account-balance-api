package services;

import domain.abstractions.TimeProvider;

import java.util.Date;

public class TimeProviderImpl implements TimeProvider {
    @Override
    public Date now() {
        return new Date();
    }
}
