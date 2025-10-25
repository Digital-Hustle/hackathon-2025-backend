-- Затем создаем таблицу events
CREATE TABLE events
(
    id              UUID PRIMARY KEY,
    title           VARCHAR(255) NOT NULL,
    date            TIMESTAMP,
    duration        INTERVAL,
    price           DECIMAL(10, 2),
    age_restriction INTEGER DEFAULT 0,
    place_id        UUID,
    FOREIGN KEY (place_id) REFERENCES places (id) ON DELETE SET NULL
);