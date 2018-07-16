DELETE FROM user_roles;
DELETE FROM users;
DELETE FROM meals;

ALTER SEQUENCE global_seq RESTART WITH 100000;


INSERT INTO users (name, email, password) VALUES
  ('User', 'user@yandex.ru', 'password'),
  ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id) VALUES
  ('ROLE_USER', 100000),
  ('ROLE_ADMIN', 100001);

INSERT INTO MEALS (user_id, date_time, description, calories) VALUES
  (100000, '2015-05-30 10:00', 'завтрак', 500),
  (100000,'2015-05-30 13:00' , 'обед', 1000),
  (100000, '2015-05-30 20:00', 'ужин', 500),
  (100001, '2015-05-31 10:00','завтрак', 500),
  (100001, '2015-05-31 13:00','обед', 1000),
  (100001, '2015-05-31 20:00','ужин', 510);