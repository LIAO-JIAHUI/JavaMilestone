insert into milestones (title, description) values ('2023年1月1日:ローンチ', 'ローンチ');
insert into milestones (title, description) values ('2022年10月31日:プレリリース', '早期リリース');
insert into milestones (title, description) values ('2022年7月10日:デモ開始', 'デモ作成中');


INSERT INTO USERS (USERNAME, PASSWORD, ENABLED) VALUES
('admin', '{bcrypt}$2a$10$vC.r53zKYPwEXplBYH3mxuZP52r2u3udRcEg9yTUmwYE5yjmoUXyG', true);
INSERT INTO AUTHORITIES (USERNAME, AUTHORITY) VALUES ('admin', 'ROLE_ADMIN');
INSERT INTO AUTHORITIES (USERNAME, AUTHORITY) VALUES ('admin', 'ROLE_USER');


INSERT INTO USERS (USERNAME, PASSWORD, ENABLED) VALUES
('user', '{bcrypt}$2a$10$vC.r53zKYPwEXplBYH3mxuZP52r2u3udRcEg9yTUmwYE5yjmoUXyG', true);
INSERT INTO AUTHORITIES (USERNAME, AUTHORITY) VALUES ('user', 'ROLE_USER');