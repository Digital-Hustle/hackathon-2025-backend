-- liquibase formatted sql

-- changeset dasemenov:120526-1014-create-reset-password-tokens-table
CREATE TABLE IF NOT EXISTS password_reset_token (
    id UUID PRIMARY KEY,
    token VARCHAR(255) NOT NULL UNIQUE,
    user_id UUID NOT NULL REFERENCES auth_users(id) ON DELETE CASCADE,
    expiry_date TIMESTAMP NOT NULL,
    used BOOLEAN NOT NULL DEFAULT FALSE
);
-- rollback DROP TABLE password_reset_token;