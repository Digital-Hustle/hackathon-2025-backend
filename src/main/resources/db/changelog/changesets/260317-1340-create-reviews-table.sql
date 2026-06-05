-- liquibase formatted sql

-- changeset dasemenov:260317-1213-create-reviews-table
CREATE TABLE reviews (
    id UUID PRIMARY KEY,
    profile_id UUID NOT NULL,
    rate SMALLINT NOT NULL CHECK (rate >= 0 AND rate <= 5) DEFAULT 0,
    comment VARCHAR(255),
    created_at TIMESTAMPTZ NOT NULL,
    updated_at TIMESTAMPTZ
);
-- rollback DROP TABLE place_reviews;
