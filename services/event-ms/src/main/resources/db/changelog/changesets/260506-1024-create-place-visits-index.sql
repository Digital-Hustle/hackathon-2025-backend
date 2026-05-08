-- liquibase formatted sql

-- changeset dasemenov:260320-1527-create-event-scores-indexes
CREATE INDEX idx_place_visits ON places (total_visits);
-- rollback DROP INDEX idx_event_scores_id; DROP INDEX idx_event_scores_score;
