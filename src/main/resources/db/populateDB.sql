DELETE
FROM user_roles;
DELETE
from votes;
DELETE
from menu;
delete
from dishes;
delete
from restaurants;
DELETE
FROM users;

ALTER SEQUENCE global_seq
  RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('Alla', 'alla@yandex.ru', '{noop}password'),
       ('Artemis', 'artemis@yandex.ru', '{noop}password'),
       ('Ksandr', 'ksandr@yandex.ru', '{noop}password'),
       ('Admin', 'admin@gmail.com', '{noop}admin');

INSERT INTO user_roles (role, user_id)
VALUES ('ROLE_USER', 100000),
       ('ROLE_USER', 100001),
       ('ROLE_USER', 100002),
       ('ROLE_ADMIN', 100003),
       ('ROLE_USER', 100003);

INSERT INTO restaurants (id, name, address)
VALUES (1, 'Прага', 'улица Арбат'),
       (2, 'Корчма', 'Рязанский проспект'),
       (3, 'Пушкин', 'Пушкинская площадь');


INSERT INTO dishes (id, name, price)
values (6, 'Гренки', 10.0),
       (7, 'Суп-лапша куриный', 65.0),
       (8, 'Котлеты по-Киевски', 135.5),
       (9, 'Рогалик', 20.0),
       (10, 'Морс 1 л', 120.0),
       (11, 'Рис', 50.0),
       (12, 'Картофельное пюре', 65.0),
       (13, 'Треска в кляре', 235.0),
       (14, 'Бульон с яйцом', 70.0),
       (15, 'Шашлык', 240.0),
       (16, 'Пахлава', 67.0),
       (17, 'Биточки куриные', 130.0),
       (18, 'Салат Цезарь', 80.0),
       (19, 'Чай', 15.0),
       (20, 'Салат Витаминный', 55.0);

insert into menu (created, id, restaurant_id)
values ('2019-04-30', 21, 1),
       ('2019-05-01', 22, 1),
       ('2019-04-30', 23, 2),
       ('2019-05-01', 24, 2),
       (default, 25, 3),
       (default, 26, 1);

INSERT INTO menu_dishes (menu_id, dishes_id)
values (21, 7),
       (21, 8),
       (21, 12),
       (21, 10),
       (21, 18),
       (23, 14),
       (23, 15),
       (23, 18),
       (23, 16),
       (23, 19),
       (25, 13),
       (25, 12),
       (25, 10),
       (25, 17),
       (25, 7),
       (25, 8),
       (26, 12),
       (26, 10),
       (26, 18),
       (22, 14),
       (22, 15),
       (22, 18),
       (22, 16),
       (22, 19),
       (24, 13),
       (24, 12),
       (24, 10),
       (24, 17);

INSERT INTO votes (registered, user_id, restaurant_id)
values ('2019-04-30', 100000, 1),
       ('2019-04-30', 100001, 1),
       ('2019-04-30', 100002, 2),
       ('2019-05-14', 100000, 3),
       ('2019-05-14', 100001, 1),
       ('2019-05-14', 100002, 1),
       (DEFAULT, 100000, 3),
       (DEFAULT, 100001, 1),
       (DEFAULT, 100002, 1);




