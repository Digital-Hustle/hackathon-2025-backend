package rnd.sueta.service.business;

import rnd.sueta.model.EventWithPlace;
import rnd.sueta.model.entity.Event;

public interface EventRegistrationService {

    Event createEvent(Event event);

    EventWithPlace updateEvent(EventWithPlace event);
}
