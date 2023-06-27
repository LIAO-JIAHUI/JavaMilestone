create table users(
	username varchar_ignorecase(50) not null primary key,
	password varchar_ignorecase(500) not null,
	display_name VARCHAR(256),
	enabled boolean not null,
	is_dark boolean not null DEFAULT false,
	icon longtext
);

create table authorities (
	username varchar_ignorecase(50) not null,
	authority varchar_ignorecase(50) not null,
	constraint fk_authorities_users foreign key(username) references users(username)
);

create unique index ix_auth_username on authorities (username,authority);

create table groups(
	id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
	name VARCHAR(256) NOT NULL
);

create table milestones (
    id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
	author varchar_ignorecase(50) not null,
	group_id BIGINT,
    title VARCHAR(256) NOT NULL,
    description TEXT NOT NULL,
	status VARCHAR(256) NOT NULL DEFAULT 'todo',
	schedule_at	DATE,
	deadline_at	DATE,
	created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	constraint fk_milestones_users foreign key(author) references users(username),
	FOREIGN KEY (group_id) REFERENCES groups(id)
);

create table group_user(
	id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
	group_id BIGINT NOT NULL,
	username varchar_ignorecase(50) NOT NULL,
	role VARCHAR(256) NOT NULL DEFAULT 'viewer', -- owner editor viewer
	FOREIGN KEY (group_id) REFERENCES groups(id),
	FOREIGN KEY (username) REFERENCES users(username)
);