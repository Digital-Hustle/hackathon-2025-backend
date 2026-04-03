package rnd.sueta.event_ms.service.business;

import rnd.sueta.event_ms.model.EventWithPlace;
import rnd.sueta.event_ms.model.entity.Event;

public interface EventRegistrationService {

    Event createEvent(Event event);

    EventWithPlace updateEvent(EventWithPlace event);
}
