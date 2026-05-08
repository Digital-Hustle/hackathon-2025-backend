-- liquibase formatted sql

-- changeset dasemenov:260320-1517-create-materialized-view-event-scores
CREATE MATERIALIZED VIEW IF NOT EXISTS event_scores AS
WITH
    event_calc AS (
        SELECT
            e.id AS event_id,
            e.total_rating,
            e.reviews_amount,
            e.event_start,
            e.event_end,
            e.recommended,

        -- Bayesian Average (assumes total_rating is SUM of rating points)
        (e.total_rating + 20.0) / NULLIF(e.reviews_amount + 5, 0) AS bayesian_avg,

        -- Time Relevance Score
        CASE
            WHEN e.event_end < NOW() THEN 0.0
            WHEN e.event_start > NOW() THEN
                LEAST(1.0, GREATEST(0.0,
                    1.0 / (1.0 + EXP(0.5 * (EXTRACT(EPOCH FROM (e.event_start - NOW())) / 86400.0 - 7)))
                ))
            WHEN e.event_start <= NOW() AND e.event_end > NOW() THEN
                LEAST(1.0, GREATEST(0.3,
                    0.3 + 0.7 * (
                        EXTRACT(EPOCH FROM (e.event_end - NOW())) /
                        NULLIF(EXTRACT(EPOCH FROM (e.event_end - e.event_start)), 0)
                    )
                ))
            ELSE 0.0
        END AS time_score
    FROM events e
    WHERE e.event_end >= NOW()
    )
SELECT
    event_id,
    ROUND(
        LEAST(1.0,
            (0.6 * ((bayesian_avg - 1.0) / 4.0) + 0.4 * time_score)
            + CASE WHEN recommended THEN 0.2 ELSE 0.0 END
        ), 4
    ) AS score
FROM event_calc;
-- rollback DROP MATERIALIZED VIEW event_scores;
