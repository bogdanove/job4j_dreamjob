CREATE TABLE users
(
    id       SERIAL PRIMARY KEY,
    email    varchar,
    password varchar
);

ALTER TABLE users ADD CONSTRAINT email_unique UNIQUE (email);