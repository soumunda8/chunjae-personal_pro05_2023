CREATE DATABASE tsherpa_personal;

USE tsherpa_personal;

CREATE TABLE role(
	role_id INT PRIMARY KEY AUTO_INCREMENT,
	role VARCHAR(255) DEFAULT NULL
);

INSERT INTO ROLE VALUES(DEFAULT, 'ADMIN');
INSERT INTO ROLE VALUES(DEFAULT, 'EMP');
INSERT INTO ROLE VALUES(DEFAULT, 'TEACHER');
INSERT INTO ROLE VALUES(DEFAULT, 'USER');

CREATE TABLE user(
	user_id INT PRIMARY KEY AUTO_INCREMENT,
	active INT DEFAULT 0, 
	login_id VARCHAR(255) NOT NULL,
	user_name VARCHAR(255) NOT NULL,
	password VARCHAR(300) NOT NULL
);

CREATE TABLE user_role(
	user_id INT NOT NULL,
	role_id INT NOT NULL,
	PRIMARY KEY (user_id, role_id)
);

CREATE VIEW userList AS(
	SELECT u.user_id AS user_id, u.active AS ACTIVE, u.login_id AS login_id, u.user_name AS user_name, u.password AS PASSWORD, r.role_id AS role_id, r.role AS roleNm
	FROM user u
	LEFT JOIN user_role ur ON u.user_id = ur.user_id
	LEFT JOIN role r ON ur.role_id = r.role_id
);