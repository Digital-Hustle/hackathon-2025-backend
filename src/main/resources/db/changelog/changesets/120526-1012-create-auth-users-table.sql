CREATE TABLE IF NOT EXISTS auth_users(
    id         UUID      DEFAULT gen_random_uuid() PRIMARY KEY,
    email   varchar(255) NOT NULL UNIQUE,
    password   varchar(512) NOT NULL,
    is_active  boolean,
    created_at timestamp,
    deleted_at timestamp,
    last_login timestamp    NULL
);

CREATE INDEX idx_auth_users_email ON auth_users (email);
