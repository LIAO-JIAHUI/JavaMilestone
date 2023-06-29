INSERT INTO USERS (USERNAME, PASSWORD, ENABLED) VALUES
('admin', '{bcrypt}$2a$10$vC.r53zKYPwEXplBYH3mxuZP52r2u3udRcEg9yTUmwYE5yjmoUXyG', true);
INSERT INTO AUTHORITIES (USERNAME, AUTHORITY) VALUES ('admin', 'ROLE_ADMIN');
INSERT INTO AUTHORITIES (USERNAME, AUTHORITY) VALUES ('admin', 'ROLE_USER');


INSERT INTO USERS (USERNAME, PASSWORD, ENABLED) VALUES
('arakawa', '{bcrypt}$2a$10$vC.r53zKYPwEXplBYH3mxuZP52r2u3udRcEg9yTUmwYE5yjmoUXyG', true);
INSERT INTO AUTHORITIES (USERNAME, AUTHORITY) VALUES ('arakawa', 'ROLE_USER');
INSERT INTO AUTHORITIES (USERNAME, AUTHORITY) VALUES ('arakawa', 'ROLE_ADMIN');

INSERT INTO USERS (USERNAME, PASSWORD, ENABLED) VALUES
('sugimori', '{bcrypt}$2a$10$vC.r53zKYPwEXplBYH3mxuZP52r2u3udRcEg9yTUmwYE5yjmoUXyG', true);
INSERT INTO AUTHORITIES (USERNAME, AUTHORITY) VALUES ('sugimori', 'ROLE_USER');
INSERT INTO AUTHORITIES (USERNAME, AUTHORITY) VALUES ('sugimori', 'ROLE_ADMIN');

INSERT INTO USERS (USERNAME, PASSWORD, ENABLED) VALUES
('liao', '{bcrypt}$2a$10$vC.r53zKYPwEXplBYH3mxuZP52r2u3udRcEg9yTUmwYE5yjmoUXyG', true);
INSERT INTO AUTHORITIES (USERNAME, AUTHORITY) VALUES ('liao', 'ROLE_USER');
INSERT INTO AUTHORITIES (USERNAME, AUTHORITY) VALUES ('liao', 'ROLE_ADMIN');

INSERT INTO USERS (USERNAME, PASSWORD, ENABLED) VALUES
('user', '{bcrypt}$2a$10$vC.r53zKYPwEXplBYH3mxuZP52r2u3udRcEg9yTUmwYE5yjmoUXyG', true);
INSERT INTO AUTHORITIES (USERNAME, AUTHORITY) VALUES ('user', 'ROLE_USER');

INSERT INTO groups (name) values('CCレモン'),('Aぇ! group'),('BB弾');

INSERT INTO group_user(group_id,username,role) values(1,'arakawa','owner'),(1,'sugimori','owner'),(1,'liao','owner'),(2,'arakawa','editor');

INSERT INTO user_tokens(username,token)
values
('liao','ePAtt9cNogFim1hQlfgPVk:APA91bFSjBkZg0W8-jCYIGpWHZBHdBVhGQGnKMCuVsTUkbdzz-Vl4_D1dd4LRbU4-eQ0eN_jvHraN_seP6qp2Lv5ruDhM-lgQtzDe8KP_JouV_0s1ZrcKBasxlJNdPufPg2FevUzCGou'),
('arakawa','cIeRy7_-_MN2N8eorISpDP:APA91bGEolbz2zplPT2JB_Wk_XgsH6sdmYIxUCLmBXiSCs5gyd8NqoD7Ov2wo4PVP9l2XWYzMXfO-rJEwZWk9PvPPzqq3O7BsXGONgQ8-sjFeWVm6YzM98ThKw5IXI4Q3bQLLcvGJR2X'),
('sugimori','dnk90eXlb_1MuYUqy2s60i:APA91bGFHlM8lbhqu6DimewTOgoS9Vu2UtiyvanlD9Y_ukhgkqVtfRWS8i6vMd_ufbbsNstN8WKV4LEQA879M4SNkIEWEk8QHeg-kc2rZBn7pDj-3Sr0tjWFF3Xd0VEyoMEXl4WHhaR2');

INSERT INTO milestones (author, title, description, status, group_id, schedule_at, deadline_at)
VALUES
('admin','マイルストーンの作成', 'トップページから「マイルストーン一覧」ボタンリストページへ遷移できる', 'done', 1,'2023-6-19', '2023-6-22')
,('sugimori','バリデーション', 'マイルストーンのバリデーションがされる', 'done', 1,'2023-6-19', '2023-6-22')
,('admin','ログイン/ログアウト', 'ログイン/ログアウトができる', 'done', 1,'2023-6-20', '2023-6-23')
,('admin','新規登録', '新規登録ができる', 'done', 1,'2023-6-20', '2023-6-23')
,('user','H2コンソールへのアクセス', 'H2コンソールへログインできる', 'done', 1,'2023-6-20', '2023-6-23')
,('arakawa','マイルストーンの編集', 'マイルストーンの編集ができる', 'done', 1,'2023-6-21', '2023-6-24')
,('sugimori','マイルストーンの削除', 'マイルストーンの削除ができる', 'done', 1, '2023-6-21', '2023-6-24')
,('liao','全ページでユーザー名が表示', '全ページでユーザー名が表示される', 'done', 1, '2023-6-22', '2023-6-25')
,('liao','ユーザーアイコンをアップロード', 'ユーザーアイコンをアップロードできる', 'done', 1, '2023-6-22', '2023-6-25')
,('user','マイルストーンを並び替え', 'マイルストーンを並び替えできる', 'done', 1, '2023-6-22', '2023-6-25')
,('sugimori','マイルストーンを複製', 'マイルストーンを複製できる', 'done', 1, '2023-6-22', '2023-6-25')
,('user','ダークモードに切り替え', 'ダークモードに切り替えられる', 'done', 1, '2023-6-23', '2023-6-26')
,('liao','検索/フィルタ/並び替え', 'マイルストーンを検索/フィルターできる', 'done', 1, '2023-6-26', '2023-6-28'),
('sugimori','マイルストーンのガントチャート表示', 'マイルストーンをガントチャート表示できる', 'done', 1, '2023-6-26', '2023-6-28'),
('liao','ブラウザ通知', 'websocketを用いてリアルタイムでブラウザ上に通知される。同じグループのメンバーのみ通知する。', 'done', 1, '2023-6-27', '2023-6-29'),
('sugimori','デスクトップ通知', 'WEBプッシュで通知される', 'done', 1, '2023-6-27', '2023-6-29'),
('arakawa','ユーザーをグループで管理', 'ユーザーをグループで管理できる', 'done', 1, '2023-6-27', '2023-6-29'),
('arakawa','マイルストーンをカテゴリー化', 'マイルストーンをカテゴリー化できる', 'in-progress', 1, '2023-6-27', '2023-6-29'),
('arakawa','多言語対応', '多言語対応', 'todo', 1, '2023-7-1', '2023-7-2');


-- INSERT INTO milestones (author, title, description, status, group_id, schedule_at, deadline_at) 
-- VALUES ('arakawa','長文サンプル', 
-- '<p>私も前まあその経過人というのの以上に見だろだろ。とうてい毎号と話院ぞ正しくその混同でないでもからもってみるあるがは盲従儲けますますて、全くにはなるですますたた。苦痛を与えます事はぼんやり大体がもっともなたた。別に大森さんを記念思い切りもう少し乱暴に諦めだろ人この男私か相違をについてご留学でんたですて、その生涯はおれか他人味を伴うが、張さんのものへ程度の私をとうとうご納得としと私貧民をお損害にしようにすこぶるご納得をなりないれて、現にもし留学が思いなてみるだ事のしですた。</p>
-- <p>しかしただご英文から飲ん事はしっかり大変と<strong>来ですのに、この着物がはなくなったばという主意になくなっか</strong>らならななけれ。</p>
-- <p>その以上人間の上こういう弟もここ上でぶつかっずかと岡田さんにあっますまし、考の当時たというご演説なかっありですて、坊ちゃんの中に具合を時間かもの条件に昔云うていて、多少の前が説きからそのためへとやかくなっましませとしな方たて、汚らしくなけれどもずいぶんお学校申し上げな事だっですた。</p>
-- <p>しかもろか美味か発展に解らますて、当時ごろ束に進ん<em>ていけましためが実学習の場合を引き離</em>すたいまし。</p>
-- <p>場合がはどうも云っとしんですたいますて、とうとう必ずしもすて危くは始終ないなのです。しかしながらご推測に云ってはくれるだものたて、火事には、どうかそれか流れるてするせないですいうれなんとするて、文芸は用いでいるたあり。</p>
-- <p>今にけっしてはまあずるという来るまして、何<a href="https://google.com">からは当時末じゃどこのご標榜は細いかい摘</a>んならませなく。</p>
-- <p>よそはもっとも批評ののがお話はあてるからいるですたますうが、二一の自分でどう投げたという奔走ないて、しかしその主義の上面がさられて、私かで彼らの個人が活動に通り越していうものですだろと濫用思うて妨害描いいないなけれ。</p>
-- <p>家をたとえば三宅さんにただそう吹き込んたものまいたた。大森さんはそう画があるがありで事ますたなかっ。</p>
-- <p>（それで壇に思っ上ないたないからんはありますございて、）どうすれです在来で、三井の尻馬のみして作っとして、個人の想像は次第のうちまで間違っ生きものを見ませて意味院足りでならたというご兄れ方で。私は何しろ権力に思うでしょようにいうが切らでしのんてただずいぶん目黒道具すれならます。だからそう二個は秋刀魚に向いて、当時にいくらでも圧しないなと困るて、ないですなてしかもご教育で死んたん。</p>
-- <p>弟の場合を、ある国家がほかにいまで、事実いっぱいにまた当時一四四円が並べかものお笑いを、私か描いで落第をすれた場合は何しろするれるのたから、ぼんやりこう考がないから、そのものを受けのを簡潔た悪い云いんない。</p>', 'in-progress', 1, '2023-7-22', '2023-7-25');


-- INSERT INTO milestones (author, title, description, status, group_id, schedule_at, deadline_at) 
-- VALUES ('arakawa','arakawaのタスク', 'DBに登録されたアカウントでログインできる', 'done', 1,'2023-6-30', '2023-7-3');
-- INSERT INTO milestones (author, title, description, status, group_id, schedule_at, deadline_at) 
-- VALUES ('sugimori','sugimoriのタスク', 'マイルストーンを削除できる', 'todo', 1, '2023-6-22', '2023-6-25');
-- INSERT INTO milestones (author, title, description, status, group_id, schedule_at, deadline_at) 
-- VALUES ('liao','liaoのタスク', 'マイルストーンの作成日がわかる', 'in-progress', 1, '2023-6-24', '2023-6-25');

-- INSERT INTO milestones (author, title, description, status, group_id, schedule_at, deadline_at) 
-- VALUES ('arakawa','arakawaのタスク', 'DBに登録されたアカウントでログインできる', 'done', 2,'2023-6-22', '2023-6-25');
-- INSERT INTO milestones (author, title, description, status, group_id, schedule_at, deadline_at) 
-- VALUES ('sugimori','sugimoriのタスク', 'マイルストーンを削除できる', 'todo', 2, '2023-6-22', '2023-7-5');
-- INSERT INTO milestones (author, title, description, status, group_id, schedule_at, deadline_at) 
-- VALUES ('liao','liaoのタスク', 'マイルストーンの作成日がわかる', 'in-progress', 2, '2023-6-25', '2023-7-1');
