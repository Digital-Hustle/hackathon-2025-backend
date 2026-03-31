-- liquibase formatted sql

-- changeset dasemenov:260206-1311-create-routes-table
CREATE TABLE IF NOT EXISTS routes
(
    id UUID PRIMARY KEY,
    profile_id UUID,
    created_at TIMESTAMPTZ DEFAULT NOW(),
    date DATE
);
-- rollback DROP TABLE routes;