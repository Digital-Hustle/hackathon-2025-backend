package rnd.sueta.event_ms.service.business.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import rnd.sueta.event_ms.model.EventWithPlace;
import rnd.sueta.event_ms.model.entity.Event;
import rnd.sueta.event_ms.service.business.EventRegistrationService;
import rnd.sueta.event_ms.service.entity.EventService;
import rnd.sueta.event_ms.service.entity.PlaceService;
import rnd.sueta.event_ms.validator.PlaceValidator;

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
    public EventWithPlace updateEvent(EventWithPlace event) {
        boolean exists = placeService.exists(event.placeId());
        PlaceValidator.checkPlaceExistence(exists);

        return eventService.update(event);
    }
}
