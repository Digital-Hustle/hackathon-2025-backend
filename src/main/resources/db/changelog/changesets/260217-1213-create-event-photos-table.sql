-- liquibase formatted sql

-- changeset dasemenov:260217-1211-create-event-photos-table
CREATE TABLE event_photos (
    owner_id UUID NOT NULL REFERENCES events(id) ON DELETE CASCADE,
    photo_id UUID NOT NULL REFERENCES photos(id) ON DELETE CASCADE,

    PRIMARY KEY (owner_id, photo_id)
);
-- rollback DROP TABLE event_photos;
