package rnd.sueta.event_ms.service.business.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import rnd.sueta.event_ms.gateway.RedisGateway;
import rnd.sueta.event_ms.model.ItemWithScore;
import rnd.sueta.event_ms.model.PlaceScoreParts;
import rnd.sueta.event_ms.model.PlaceWithCoordinates;
import rnd.sueta.event_ms.service.business.CacheProvider;
import rnd.sueta.event_ms.service.entity.PlaceService;
import rnd.sueta.event_ms.util.PlaceScoreCalculator;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PlaceCacheProviderImpl implements CacheProvider<PlaceWithCoordinates> {

    private final PlaceService placeService;
    private final RedisGateway<PlaceWithCoordinates> placeRedisGateway;

    @Override
    public Page<PlaceWithCoordinates> getTop(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        long total = placeRedisGateway.getSetSize();
        if (total == 0L) {
            rebuildTop();
            total = placeRedisGateway.getSetSize();
        }

        long start = pageable.getOffset();
        long end = start + pageable.getPageSize() - 1;
        if (total == 0L || start >= total) {
            return getEmptyPage(total, pageable);
        }

        List<UUID> placeIds = placeRedisGateway.getOrderedKeys(start, end);
        List<PlaceWithCoordinates> places = placeRedisGateway.getAll(placeIds);

        return new PageImpl<>(places, pageable, total);
    }

    @Override
    public PlaceWithCoordinates getById(UUID placeId) {
        return placeRedisGateway.findById(placeId)
                .orElseGet(() -> placeService.getById(placeId));
    }

    @Override
    public PlaceWithCoordinates update(PlaceWithCoordinates place) {
        PlaceWithCoordinates updatedPlace = placeService.update(place);
        PlaceScoreParts scoreParts = placeService.getScorePartsById(place.id());

        placeService.refreshTop();

        double score = PlaceScoreCalculator.calculateScore(scoreParts);
        double minScoreInSortedSet = placeRedisGateway.getMinScore();
        if (score > minScoreInSortedSet) {
            ItemWithScore<PlaceWithCoordinates> topPlaceWithScore = ItemWithScore.<PlaceWithCoordinates>builder()
                    .itemId(updatedPlace.id())
                    .score(score)
                    .item(updatedPlace)
                    .build();
            placeRedisGateway.cacheOne(topPlaceWithScore);
        }

        return updatedPlace;
    }

    @Override
    public void rebuildTop() {
        placeService.refreshTop();
        placeRedisGateway.deleteOldValues();

        List<ItemWithScore<PlaceWithCoordinates>> topPlaces = placeService.getTopWithScores();
        if (topPlaces.isEmpty()) {
            return;
        }

        placeRedisGateway.cacheAll(topPlaces);
    }

    @Override
    public void delete(UUID id) {
        placeService.delete(id);
        placeRedisGateway.delete(id);
        placeService.refreshTop();
    }

    @Nullable
    private Page<PlaceWithCoordinates> getEmptyPage(long total, Pageable pageable) {
        if (total == 0L) {
            return new PageImpl<>(List.of(), pageable, 0L);
        }

        long start = pageable.getOffset();
        if (start >= total) {
            return new PageImpl<>(List.of(), pageable, total);
        }

        return null;
    }
}
