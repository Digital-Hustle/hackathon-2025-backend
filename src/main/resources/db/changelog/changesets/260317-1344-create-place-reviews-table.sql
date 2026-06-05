-- liquibase formatted sql

-- changeset dasemenov:260317-1213-create-place-reviews-table
CREATE TABLE place_reviews (
    owner_id UUID NOT NULL REFERENCES places(id) ON DELETE CASCADE,
    review_id UUID NOT NULL REFERENCES reviews(id) ON DELETE CASCADE,

    PRIMARY KEY (owner_id, review_id)
);
-- rollback DROP TABLE place_reviews;
