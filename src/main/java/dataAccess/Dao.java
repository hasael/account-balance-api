package dataAccess;

import dataAccess.dto.BaseDto;
import dataAccess.dto.UUID;
import org.apache.commons.lang3.tuple.Pair;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Dao<T extends BaseDto> {

    private final HashMap<UUID, T> data;
    private final UIDGenerator uidGenerator;

    public Dao(HashMap<UUID, T> data, UIDGenerator uidGenerator) {
        this.data = data;
        this.uidGenerator = uidGenerator;
    }

    public Optional<T> read(UUID uuid) {
        T value = data.getOrDefault(uuid, null);
        return value != null ? Optional.of(value) : Optional.empty();
    }

    public Pair<UUID, T> create(T value) {
        UUID id = uidGenerator.generateId();
        data.put(id, value);
        return Pair.of(id, value);
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
