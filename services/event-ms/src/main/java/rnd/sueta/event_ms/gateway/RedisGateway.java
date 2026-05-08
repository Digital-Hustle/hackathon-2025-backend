package rnd.sueta.event_ms.gateway;

import rnd.sueta.event_ms.model.ItemWithScore;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RedisGateway<T> {

    List<UUID> getOrderedKeys(long start, long end);

    List<T> getAll(List<UUID> ids);

    Optional<T> findById(UUID id);

    double getMinScore();

    long getSetSize();

    void cacheAll(List<ItemWithScore<T>> objectsWithScores);

    void cacheOne(ItemWithScore<T> objWithScore);

    void deleteOldValues();

    void delete(UUID id);
}
