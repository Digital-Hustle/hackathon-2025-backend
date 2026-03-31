-- liquibase formatted sql

-- changeset dasemenov:260320-1517-create-materialized-view-event-scores
CREATE MATERIALIZED VIEW IF NOT EXISTS event_scores AS
WITH max_reviews_cte AS (
    SELECT COALESCE(MAX(reviews_amount), 0) AS max_reviews FROM events
)
SELECT
    e.id AS event_id,
    ROUND(
        (
            -- Рейтинговая часть (байесовский средний → нормализованный [0..1] * вес 0.5)
            ((COALESCE(e.total_rating, 0) + 10 * 4.0) / (COALESCE(e.reviews_amount, 0) + 10) - 1) / 4 * 0.5
            +
            -- Популярность (нормированная на максимум отзывов * вес 0.3)
            COALESCE(e.reviews_amount / NULLIF((SELECT max_reviews FROM max_reviews_cte), 0), 0) * 0.3
            +
            -- Актуальность: логистическая функция от дней до старта (вес 0.2)
            CASE
                WHEN e.event_end < NOW() THEN 0
                WHEN e.event_start < NOW() THEN 1   -- уже идёт, максимальный вес
                ELSE 1 / (1 + EXP(-0.5 * (EXTRACT(DAY FROM (e.event_start - NOW())) - 7)))
            END * 0.2
        )
        * (1 + CASE WHEN e.recommended THEN 0.2 ELSE 0 END),
        6
    ) AS score
FROM events e
WHERE e.event_end >= NOW();
-- rollback DROP MATERIALIZED VIEW event_scores;
