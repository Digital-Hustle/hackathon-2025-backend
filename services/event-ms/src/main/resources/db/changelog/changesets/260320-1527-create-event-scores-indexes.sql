-- liquibase formatted sql

-- changeset dasemenov:260320-1527-create-event-scores-indexes
CREATE UNIQUE INDEX idx_event_scores_id ON event_scores (event_id);
CREATE INDEX idx_event_scores_score ON event_scores (score DESC);
-- rollback DROP INDEX idx_event_scores_id; DROP INDEX idx_event_scores_score;
