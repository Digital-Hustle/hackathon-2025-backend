package rnd.sueta.event_ms.service.business;

import rnd.sueta.event_ms.model.entity.Event;

import java.util.UUID;

public interface EventRegistrationService {

    Event createEvent(Event event);

    Event updateEvent(UUID id, Event event);
}
