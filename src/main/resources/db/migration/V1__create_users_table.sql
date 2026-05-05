-- V1__create_users_table.sql
CREATE TABLE users
(
    id   BIGSERIAL  PRIMARY KEY,
    name VARCHAR(100) NOT NULL
);