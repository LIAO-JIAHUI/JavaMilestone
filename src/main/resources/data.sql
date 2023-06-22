INSERT INTO USERS (USERNAME, PASSWORD, ENABLED) VALUES
('admin', '{bcrypt}$2a$10$vC.r53zKYPwEXplBYH3mxuZP52r2u3udRcEg9yTUmwYE5yjmoUXyG', true);
INSERT INTO AUTHORITIES (USERNAME, AUTHORITY) VALUES ('admin', 'ROLE_ADMIN');
INSERT INTO AUTHORITIES (USERNAME, AUTHORITY) VALUES ('admin', 'ROLE_USER');


INSERT INTO USERS (USERNAME, PASSWORD, ENABLED) VALUES
('user', '{bcrypt}$2a$10$vC.r53zKYPwEXplBYH3mxuZP52r2u3udRcEg9yTUmwYE5yjmoUXyG', true);
INSERT INTO AUTHORITIES (USERNAME, AUTHORITY) VALUES ('user', 'ROLE_USER');


INSERT INTO milestones (author, title, description, status, schedule_at, deadline_at) 
VALUES ('admin','リストページへ遷移', 'トップページから「マイルストーン一覧」ボタンリストページへ遷移できる', 'done', '2023-6-22', '2023-6-25');
INSERT INTO milestones (author, title, description, status, schedule_at, deadline_at) 
VALUES ('user','DBログインできる', 'DBに登録されたアカウントでログインできる', 'done', '2023-6-22', '2023-6-25');
INSERT INTO milestones (author, title, description, status, schedule_at, deadline_at) 
VALUES ('user','マイルストーンの削除', 'マイルストーンを削除できる', 'todo', '2023-6-22', '2023-6-25');
INSERT INTO milestones (author, title, description, status) 
VALUES ('user','作成日の追加', 'マイルストーンの作成日がわかる', 'in-progress');
