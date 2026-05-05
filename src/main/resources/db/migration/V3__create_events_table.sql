-- V3__create_events_table.sql
CREATE TABLE events
(
    id            BIGSERIAL  PRIMARY KEY,
    user_id       BIGSERIAL    NOT NULL,
    file_id       BIGSERIAL    NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    FOREIGN KEY (file_id) REFERENCES files (id) ON DELETE CASCADE
);