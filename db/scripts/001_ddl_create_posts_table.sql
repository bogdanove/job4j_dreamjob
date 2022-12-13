CREATE TABLE post
(
    id          SERIAL PRIMARY KEY,
    name        varchar,
    description TEXT,
    created     date,
    city_id     int
);

INSERT INTO post(name, description, created, city_id)
VALUES ('Junior Java Job', 'Ты научишься использовать инструмент сборки Maven. Будешь писать модульные тесты и оформлять свой код', CURRENT_TIMESTAMP, 1);
INSERT INTO post(name, description, created, city_id)
VALUES ('Middle Java Job', 'В этом уровне ты создашь парсер вакансий популярного сайта', CURRENT_TIMESTAMP, 2);
INSERT INTO post(name, description, created, city_id)
VALUES ('Senior Java Job', 'Ты научишься использовать Java фреймворки, которые используют профессионалы каждый день', CURRENT_TIMESTAMP, 3);