package dataAccess;

import dataAccess.dto.BaseDto;
import dataAccess.dto.UUID;
import domain.responses.ErrorResponse;
import domain.responses.Response;
import domain.responses.Success;
import org.apache.commons.lang3.tuple.Pair;

import java.util.HashMap;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static domain.responses.NotFound.notFound;

public class Dao<T extends BaseDto> {

    private final HashMap<UUID, T> data;
    private final UIDGenerator uidGenerator;

    public Dao(HashMap<UUID, T> data, UIDGenerator uidGenerator) {
        this.data = data;
        this.uidGenerator = uidGenerator;
    }

    public Response<T> read(UUID uuid) {
        try {
            T value = data.getOrDefault(uuid, null);
            return value != null ? Success.Of(value) : notFound();
        } catch (Exception ex) {
            return ErrorResponse.Of(ex);
        }
    }

    public Response<Pair<UUID, T>> create(T value) {
        try {
            UUID id = uidGenerator.generateId();
            data.put(id, value);
            return Success.Of(Pair.of(id, value));
        } catch (Exception ex) {
            return ErrorResponse.Of(ex);
        }
    }

    public Response<T> update(T value, UUID uuid) {
        try {
            T replaced = data.replace(uuid, value);
            return replaced != null ? Success.Of(replaced) : notFound();
        } catch (Exception ex) {
            return ErrorResponse.Of(ex);
        }
    }

    public Response<T> delete(UUID uuid) {
        try {
            T removed = data.remove(uuid);
            return removed != null ? Success.Of(removed) : notFound();
        } catch (Exception ex) {
            return ErrorResponse.Of(ex);
        }
    }

    public Response<T> query(Predicate<T> predicate) {
        try {
            return Success.Of(data.values().stream().filter(predicate).collect(Collectors.toList()));
        } catch (Exception ex) {
            return ErrorResponse.Of(ex);
        }
    }
}
