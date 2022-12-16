CREATE TABLE candidate
(
    id          SERIAL PRIMARY KEY,
    name        varchar,
    description TEXT,
    created     date,
    city_id     int,
    photo       bytea
);

-- INSERT INTO candidate(name, description, created, city_id, photo)
-- VALUES ('Junior Java Candidate', 'Молчу, но все понимаю', CURRENT_TIMESTAMP, 1, null);
-- INSERT INTO candidate(name, description, created, city_id, photo)
-- VALUES ('Middle Java Candidate', 'Много делаю и молчу', CURRENT_TIMESTAMP, 2, null);
-- INSERT INTO candidate(name, description, created, city_id, photo)
-- VALUES ('Senior Java Candidate', 'Много говорю, но руками ничего не делаю', CURRENT_TIMESTAMP, 3, null);