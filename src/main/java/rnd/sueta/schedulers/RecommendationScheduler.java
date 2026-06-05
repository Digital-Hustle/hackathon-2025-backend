package rnd.sueta.schedulers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import rnd.sueta.model.EventWithPlace;
import rnd.sueta.model.PlaceWithCoordinates;
import rnd.sueta.service.business.CacheProvider;

@Slf4j
@Component
@RequiredArgsConstructor
public class RecommendationScheduler {

    private final CacheProvider<PlaceWithCoordinates> placeCacheProvider;
    private final CacheProvider<EventWithPlace> eventCacheProvider;

    @Scheduled(cron = "${app.domain.place.recommendation.rate}")
    @SchedulerLock(
            name = "PlaceRecommendationScheduler_recommendationTask",
            lockAtLeastFor = "${app.domain.place.recommendation.lock-at-least-for}",
            lockAtMostFor = "${app.domain.place.recommendation.lock-at-most-for}"
    )
    public void refreshPlaceRecommendations() {
        log.info("Refresh place recommendation materialized view");

        placeCacheProvider.rebuildTop();
    }

    @Scheduled(cron = "${app.domain.event.recommendation.rate}")
    @SchedulerLock(
            name = "EventRecommendationScheduler_recommendationTask",
            lockAtLeastFor = "${app.domain.event.recommendation.lock-at-least-for}",
            lockAtMostFor = "${app.domain.event.recommendation.lock-at-most-for}"
    )
    public void refreshEventRecommendations() {
        log.info("Refresh event recommendation materialized view");

        eventCacheProvider.rebuildTop();
    }
}
