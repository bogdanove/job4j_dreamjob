CREATE TABLE users
(
    id       SERIAL PRIMARY KEY,
    name varchar,
    email    varchar,
    password varchar
);

ALTER TABLE users ADD CONSTRAINT email_unique UNIQUE (email);