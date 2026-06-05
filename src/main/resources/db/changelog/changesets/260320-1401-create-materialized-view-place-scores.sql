-- liquibase formatted sql

-- changeset dasemenov:260320-1401-create-materialized-view-place-scores
CREATE MATERIALIZED VIEW IF NOT EXISTS place_scores AS
WITH max_visits_cte AS (
    SELECT COALESCE(MAX(total_visits), 0) AS max_visits FROM places
)
SELECT
    p.id AS place_id,
    ROUND(
        (
            -- Рейтинговая часть (байесовский средний → нормализованный [0..1] * вес 0.6)
            ((COALESCE(p.total_rating, 0) + 10 * 4.0) / (COALESCE(p.reviews_amount, 0) + 10) - 1) / 4 * 0.6
            +
            -- Популярность (нормированная на максимум посещений * вес 0.4)
            COALESCE(p.total_visits / NULLIF((SELECT max_visits FROM max_visits_cte), 0), 0) * 0.4
        )
        -- Бонус за платное продвижение (+20% к результату)
        * (1 + CASE WHEN p.recommended THEN 0.2 ELSE 0 END),
        6
    ) AS score
FROM places p;
-- rollback DROP MATERIALIZED VIEW place_scores;
