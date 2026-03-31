package rnd.sueta.event_ms.service.entity.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import rnd.sueta.event_ms.model.EventWithPlace;
import rnd.sueta.event_ms.model.PlaceWithCoordinates;
import rnd.sueta.event_ms.repository.RecommendationRepository;
import rnd.sueta.event_ms.service.entity.RecommendationService;

@Service
@RequiredArgsConstructor
public class RecommendationServiceImpl implements RecommendationService {

    private final RecommendationRepository recommendationRepository;

    @Override
    public Page<PlaceWithCoordinates> getAllPlaces(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        return recommendationRepository.findAllPlaces(pageable);
    }

    @Override
    public Page<EventWithPlace> getAllEvents(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        return recommendationRepository.findAllEvents(pageable);
    }

    @Override
    public void refreshPlaces() {
        recommendationRepository.refreshPlaces();
    }

    @Override
    public void refreshEvents() {
        recommendationRepository.refreshEvents();
    }
}
