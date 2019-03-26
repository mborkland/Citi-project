INSERT INTO SYSTEM_USERS (username, password) VALUES
  ('admin', '$2a$10$DkofAgO37J3iZD4DdaYXmuq4nL0iDsFhfG8vKwgOP5e661GGn2Jgu'),
  ('user', '$2a$10$DkofAgO37J3iZD4DdaYXmuq4nL0iDsFhfG8vKwgOP5e661GGn2Jgu');

INSERT INTO USER_ROLES (user_id, roles) VALUES (1, 0);
INSERT INTO USER_ROLES (user_id, roles) VALUES (1, 1);
INSERT INTO USER_ROLES (user_id, roles) VALUES (2, 1);