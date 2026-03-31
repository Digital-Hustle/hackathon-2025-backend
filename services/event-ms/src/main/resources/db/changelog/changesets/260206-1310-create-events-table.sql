-- liquibase formatted sql

-- changeset dasemenov:260206-1309-create-events-table
CREATE TABLE IF NOT EXISTS events
(
    id              UUID PRIMARY KEY,
    organizer_id    UUID NOT NULL,
    title           VARCHAR(255) NOT NULL,
    type            VARCHAR(100)   NOT NULL,
    total_rating    INT NOT NULL DEFAULT 0,
    reviews_amount  INT NOT NULL DEFAULT 0,
    event_start     TIMESTAMPTZ NOT NULL,
    event_end       TIMESTAMPTZ NOT NULL,
    price           DECIMAL(10, 2),
    age_restriction INTEGER DEFAULT 0,
    place_id        UUID NOT NULL REFERENCES places (id),
    recommended     BOOLEAN DEFAULT FALSE
);
-- rollback DROP TABLE events;
