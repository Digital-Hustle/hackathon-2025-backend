-- liquibase formatted sql

-- changeset dasemenov:260317-1213-create-event-reviews-table
CREATE TABLE event_reviews (
    owner_id UUID NOT NULL REFERENCES events(id) ON DELETE CASCADE,
    review_id UUID NOT NULL REFERENCES reviews(id) ON DELETE CASCADE,

    PRIMARY KEY (owner_id, review_id)
);
-- rollback DROP TABLE event_reviews;
