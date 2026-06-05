-- liquibase formatted sql

-- changeset dasemenov:260206-1309-create-places-table
CREATE TABLE IF NOT EXISTS places
(
    id        UUID PRIMARY KEY,
    title     VARCHAR(255) NOT NULL,
    address   VARCHAR(255) NOT NULL,
    type      VARCHAR(100) NOT NULL,
    total_rating    INT NOT NULL DEFAULT 0,
    reviews_amount INT NOT NULL DEFAULT 0,
    total_visits INT NOT NULL DEFAULT 0,
    owner_id  UUID NOT NULL,
    point_id  UUID NOT NULL REFERENCES points(id),
    recommended BOOLEAN DEFAULT FALSE,
    contacts JSONB
);
-- rollback DROP TABLE places;