CREATE TABLE if not exists routes
(
    id UUID,
    place_id UUID NOT NULL,
    profile_id UUID,
    PRIMARY KEY (id, place_id)
);

