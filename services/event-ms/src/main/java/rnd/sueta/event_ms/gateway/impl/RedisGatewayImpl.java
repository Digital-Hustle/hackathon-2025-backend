package rnd.sueta.event_ms.gateway.impl;

import lombok.RequiredArgsConstructor;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import rnd.sueta.event_ms.gateway.RedisGateway;
import rnd.sueta.event_ms.model.CacheProperties;
import rnd.sueta.event_ms.model.ItemWithScore;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@RequiredArgsConstructor
public class RedisGatewayImpl<T> implements RedisGateway<T> {

    private final StringRedisTemplate stringRedisTemplate;
    private final RedisTemplate<String, T> redisTemplate;
    private final CacheProperties cacheProperties;

    @Override
    public List<UUID> getOrderedKeys(long start, long end) {
        Set<String> members = stringRedisTemplate.opsForZSet()
                .reverseRange(cacheProperties.setKey(), start, end);

        if (CollectionUtils.isEmpty(members)) {
            return Collections.emptyList();
        }

        return members.stream()
                .map(this::tryParseUuid)
                .filter(Objects::nonNull)
                .toList();
    }

    @Override
    public List<T> getAll(List<UUID> itemIds) {
        if (CollectionUtils.isEmpty(itemIds)) {
            return Collections.emptyList();
        }

        List<String> keys = itemIds.stream()
                .filter(Objects::nonNull)
                .map(this::getItemValueKey)
                .toList();

        List<T> values = redisTemplate.opsForValue().multiGet(keys);

        if (CollectionUtils.isEmpty(values)) {
            return Collections.emptyList();
        }

        return values.stream()
                .filter(Objects::nonNull)
                .toList();
    }

    @Override
    public Optional<T> findById(UUID itemId) {
        String key = getItemValueKey(itemId);
        T item = redisTemplate.opsForValue().get(key);

        return Optional.ofNullable(item);
    }

    @Override
    public double getMinScore() {
        String zsetKey = cacheProperties.setKey();
        Set<ZSetOperations.TypedTuple<String>> results =
                stringRedisTemplate.opsForZSet().rangeWithScores(zsetKey, 0, 0);

        if (CollectionUtils.isEmpty(results)) {
            return 0;
        }

        ZSetOperations.TypedTuple<String> first = results.iterator().next();
        return Objects.requireNonNullElse(first.getScore(), 0.0);
    }

    @Override
    public long getSetSize() {
        String setKey = cacheProperties.setKey();
        Long size = stringRedisTemplate.opsForZSet().size(setKey);

        return Objects.requireNonNullElse(size, 0L);
    }

    @Override
    public void cacheAll(List<ItemWithScore<T>> itemsWithScore) {
        List<ItemWithScore<T>> filteredItems = itemsWithScore.stream()
                .filter(item -> item != null && item.item() != null && item.itemId() != null)
                .toList();

        performBatchCacheToSortedSet(filteredItems);
        performBatchCache(filteredItems);
    }

    @Override
    public void cacheOne(ItemWithScore<T> itemWithScore) {
        T item = itemWithScore.item();

        if (Objects.isNull(item)) {
            return;
        }

        String key = getItemValueKey(itemWithScore.itemId());
        redisTemplate.opsForValue().set(key, item, cacheProperties.ttl());

        String zsetKey = cacheProperties.setKey();
        String member = itemWithScore.itemId().toString();

        stringRedisTemplate.opsForZSet().add(zsetKey, member, itemWithScore.score());
        stringRedisTemplate.expire(zsetKey, cacheProperties.ttl());
    }

    @Override
    public void deleteOldValues() {
        String setKey = cacheProperties.setKey();

        Set<String> members = stringRedisTemplate.opsForZSet()
                .reverseRange(setKey, 0, -1);

        if (!CollectionUtils.isEmpty(members)) {
            List<String> keysToDelete = members.stream()
                    .map(this::tryParseUuid)
                    .filter(Objects::nonNull)
                    .map(this::getItemValueKey)
                    .toList();
            redisTemplate.delete(keysToDelete);
        }

        stringRedisTemplate.delete(setKey);
    }

    @Override
    public void delete(UUID itemId) {
        String key = getItemValueKey(itemId);
        redisTemplate.delete(key);
    }

    private void performBatchCacheToSortedSet(List<ItemWithScore<T>> itemWithScores) {
        stringRedisTemplate.executePipelined(
                (RedisCallback<Void>) connection -> {
                    byte[] zsetKeyBytes = cacheProperties.setKey().getBytes();

                    itemWithScores.forEach(item ->
                            connection.zAdd(zsetKeyBytes, item.score(), item.itemId().toString().getBytes()));

                    connection.expire(zsetKeyBytes, cacheProperties.ttl());
                    return null;
                });
    }

    private void performBatchCache(List<ItemWithScore<T>> itemsWithScore) {
        Map<String, T> valueMap = new HashMap<>();

        for (ItemWithScore<T> itemWithScore : itemsWithScore) {
            valueMap.put(getItemValueKey(itemWithScore.itemId()), itemWithScore.item());
        }

        redisTemplate.opsForValue().multiSet(valueMap);
        redisTemplate.executePipelined((RedisCallback<Void>) connection -> {
            for (String key : valueMap.keySet()) {
                connection.expire(key.getBytes(), cacheProperties.ttl());
            }
            return null;
        });
    }

    @Nullable
    private UUID tryParseUuid(String value) {
        try {
            return UUID.fromString(value);
        } catch (Exception ignored) {
            return null;
        }
    }

    private String getItemValueKey(UUID itemId) {
        return cacheProperties.keyPrefix() + itemId;
    }
}
