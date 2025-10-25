-- Сначала создаем таблицу places, так как на нее ссылается events
CREATE TABLE places
(
    id        UUID PRIMARY KEY,
    title     VARCHAR(255)   NOT NULL,
    type      VARCHAR(100),
    latitude  DECIMAL(10, 8) NOT NULL,
    longitude DECIMAL(11, 8) NOT NULL,
    address   TEXT,
    image     VARCHAR(500)
);