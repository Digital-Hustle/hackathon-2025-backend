-- liquibase formatted sql

-- changeset dasemenov:260206-1312-create-route-places-table
CREATE TABLE route_places (
    id UUID PRIMARY KEY,
    route_id UUID NOT NULL REFERENCES routes(id) ON DELETE CASCADE,
    place_id UUID NOT NULL REFERENCES places(id) ON DELETE CASCADE,
    position INT NOT NULL,
    UNIQUE(route_id, position),
    UNIQUE(route_id, place_id)
);
-- rollback DROP TABLE route_places;
