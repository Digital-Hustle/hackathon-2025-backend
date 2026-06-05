-- liquibase formatted sql

-- changeset dasemenov:260217-1211-create-place-photos-table
CREATE TABLE place_photos (
    owner_id UUID NOT NULL REFERENCES places(id) ON DELETE CASCADE,
    photo_id UUID NOT NULL REFERENCES photos(id) ON DELETE CASCADE,

    PRIMARY KEY (owner_id, photo_id)
);
-- rollback DROP TABLE place_photos;
