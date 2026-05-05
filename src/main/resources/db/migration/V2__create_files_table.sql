-- V2__create_files_table.sql
CREATE TABLE files
(
    id        BIGSERIAL  PRIMARY KEY,
    file_name VARCHAR(255) NOT NULL,
    file_path VARCHAR(500) NOT NULL UNIQUE
);