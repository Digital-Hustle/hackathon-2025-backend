-- liquibase formatted sql

-- changeset dasemenov:260211-1132-create-photos-table
CREATE TABLE photos (
     id UUID PRIMARY KEY,
     original_file_name VARCHAR(255) NOT NULL,
     owner_type VARCHAR(100) NOT NULL,
     extension VARCHAR(10) NOT NULL,
     file_size INTEGER NOT NULL,
     content_type VARCHAR(100),
     uploaded_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
-- rollback DROP TABLE photos;