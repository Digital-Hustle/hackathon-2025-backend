-- liquibase formatted sql

-- changeset dasemenov:120526-1015-create-reset-password-tokens-indexes
CREATE INDEX idx_password_reset_token_expiry_date ON password_reset_token(expiry_date);
CREATE INDEX idx_password_reset_token_user_id ON password_reset_token(user_id);
-- rollback DROP INDEX idx_password_reset_token_expiry_date; DROP INDEX idx_password_reset_token_user_id;
