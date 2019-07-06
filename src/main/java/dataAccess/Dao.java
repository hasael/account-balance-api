package dataAccess;

import dataAccess.dto.BaseDto;
import dataAccess.dto.UUID;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public abstract class Dao<T extends BaseDto> {

    private final HashMap<UUID, T> data;

    protected Dao(HashMap<UUID, T> data) {
        this.data = data;
    }

    public Optional<T> read(UUID uuid) {
        T value = data.getOrDefault(uuid, null);
        return value != null ? Optional.of(value) : Optional.empty();
    }

    public T create(T value) {
        data.put(value.getId(), value);
        return value;
    }

    public Optional<T> update(T value, UUID uuid) {
        T replaced = data.replace(uuid, value);
        return replaced != null ? Optional.of(replaced) : Optional.empty();
    }

    public Optional<T> delete(UUID uuid) {
        T removed = data.remove(uuid);
        return removed != null ? Optional.of(removed) : Optional.empty();
    }

    public List<T> query(Predicate<T> predicate) {
        return data.values().stream().filter(predicate).collect(Collectors.toList());
    }
}
