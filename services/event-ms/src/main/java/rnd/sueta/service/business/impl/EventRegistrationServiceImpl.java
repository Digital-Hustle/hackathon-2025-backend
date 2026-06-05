package rnd.sueta.service.business.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import rnd.sueta.model.EventWithPlace;
import rnd.sueta.model.entity.Event;
import rnd.sueta.service.business.EventRegistrationService;
import rnd.sueta.service.entity.EventService;
import rnd.sueta.service.entity.PlaceService;
import rnd.sueta.validator.PlaceValidator;

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
