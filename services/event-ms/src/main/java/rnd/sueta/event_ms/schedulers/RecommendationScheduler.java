package rnd.sueta.event_ms.schedulers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import rnd.sueta.event_ms.service.entity.RecommendationService;

@Slf4j
@Component
@RequiredArgsConstructor
public class RecommendationScheduler {

    private final RecommendationService recommendationService;

    @Scheduled(cron = "${app.domain.place.recommendation.rate}")
    public void refreshPlaceRecommendations() {
        log.info("Refresh place recommendation materialized view");

        recommendationService.refreshPlaces();
    }

    @Scheduled(cron = "${app.domain.event.recommendation.rate}")
    public void refreshEventRecommendations() {
        log.info("Refresh event recommendation materialized view");

        recommendationService.refreshEvents();
    }
}
