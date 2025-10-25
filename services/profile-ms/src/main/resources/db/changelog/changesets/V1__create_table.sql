CREATE EXTENSION IF NOT EXISTS pgcrypto; -- эт нужно для gen_random_uuid()

CREATE TABLE profile
(
    id         uuid PRIMARY KEY,
    name       varchar(100)                           NOT NULL,
    surname    varchar(100)                           NOT NULL,
    date_of_birth date                                NOT NULL,
    image      text,
    created_at timestamp DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamp DEFAULT CURRENT_TIMESTAMP
);

