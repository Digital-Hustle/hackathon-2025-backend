-- liquibase formatted sql

-- changeset dasemenov:260320-1414-create-place-scores-indexes
CREATE UNIQUE INDEX IF NOT EXISTS idx_place_scores_id ON place_scores (place_id);
CREATE INDEX IF NOT EXISTS idx_place_scores_score ON place_scores (score DESC);
-- rollback DROP INDEX idx_place_scores_id; DROP INDEX idx_place_scores_score;
