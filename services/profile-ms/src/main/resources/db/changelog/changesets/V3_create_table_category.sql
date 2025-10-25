CREATE EXTENSION IF NOT EXISTS pgcrypto;

CREATE TABLE IF NOT EXISTS category
(
    id uuid PRIMARY KEY,
    name varchar(50) NOT NULL UNIQUE,
    CONSTRAINT chk_category_name CHECK (
        name IN ('NATURE', 'CULTURE', 'FOOD', 'SHOPPING', 'HISTORICAL', 'ENTERTAINMENT')
    )
);