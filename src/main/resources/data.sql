insert into milestones (title, description, status) 
values ('リストページへ遷移', 'トップページから「マイルストーン一覧」ボタンリストページへ遷移できる', 'done');
insert into milestones (title, description, status) 
values ('DBログインできる', 'DBに登録されたアカウントでログインできる', 'done');
insert into milestones (title, description, status) 
values ('マイルストーンの削除', 'マイルストーンを削除できる', 'todo');
insert into milestones (title, description, status) 
values ('作成日の追加', 'マイルストーンの作成日がわかる', 'in-progress');


INSERT INTO USERS (USERNAME, PASSWORD, ENABLED) VALUES
('admin', '{bcrypt}$2a$10$vC.r53zKYPwEXplBYH3mxuZP52r2u3udRcEg9yTUmwYE5yjmoUXyG', true);
INSERT INTO AUTHORITIES (USERNAME, AUTHORITY) VALUES ('admin', 'ROLE_ADMIN');
INSERT INTO AUTHORITIES (USERNAME, AUTHORITY) VALUES ('admin', 'ROLE_USER');


INSERT INTO USERS (USERNAME, PASSWORD, ENABLED) VALUES
('user', '{bcrypt}$2a$10$vC.r53zKYPwEXplBYH3mxuZP52r2u3udRcEg9yTUmwYE5yjmoUXyG', true);
INSERT INTO AUTHORITIES (USERNAME, AUTHORITY) VALUES ('user', 'ROLE_USER');