package rnd.sueta.event_ms.service.business.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import rnd.sueta.event_ms.model.entity.Event;
import rnd.sueta.event_ms.service.business.EventRegistrationService;
import rnd.sueta.event_ms.service.entity.EventService;
import rnd.sueta.event_ms.service.entity.PlaceService;
import rnd.sueta.event_ms.validator.PlaceValidator;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EventRegistrationServiceImpl implements EventRegistrationService {

    private final EventService eventService;
    private final PlaceService placeService;

    @Override
    public Event createEvent(Event event) {
        boolean exists = placeService.exists(event.getPlaceId());
        PlaceValidator.checkPlaceExistence(exists);

        return eventService.create(event);
    }

    @Override
    public Event updateEvent(UUID id, Event event) {
        boolean exists = placeService.exists(event.getPlaceId());
        PlaceValidator.checkPlaceExistence(exists);

        return eventService.update(id, event);
    }
}
