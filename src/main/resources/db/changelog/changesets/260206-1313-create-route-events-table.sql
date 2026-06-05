-- liquibase formatted sql

-- changeset dasemenov:260206-1313-create-route-events-table
CREATE TABLE route_events (
    id UUID PRIMARY KEY,
    route_id UUID NOT NULL REFERENCES routes(id) ON DELETE CASCADE,
    event_id UUID NOT NULL REFERENCES events(id) ON DELETE CASCADE,
    UNIQUE(route_id, event_id)
);
-- rollback DROP TABLE route_events;
