CREATE DATABASE tsherpa_personal;

USE tsherpa_personal;

CREATE TABLE role(
	role_id INT PRIMARY KEY AUTO_INCREMENT,
	role VARCHAR(255) DEFAULT NULL
);

INSERT INTO ROLE VALUES(DEFAULT, 'ADMIN');
INSERT INTO ROLE VALUES(DEFAULT, 'EMP');
INSERT INTO ROLE VALUES(DEFAULT, 'TEACHER');
INSERT INTO ROLE VALUES(99, 'USER');

CREATE TABLE user(
	user_id INT PRIMARY KEY AUTO_INCREMENT,
	active INT DEFAULT 0, 
	login_id VARCHAR(255) NOT NULL,
	user_name VARCHAR(255) NOT NULL,
	password VARCHAR(300) NOT NULL,
	email VARCHAR(50) NOT NULL,
	tel VARCHAR(20) NOT NULL,
	addr1 VARCHAR(200),
	addr2 VARCHAR(100),
	postcode VARCHAR(10),
	reg_date DATETIME DEFAULT CURRENT_TIMESTAMP(),
	birth DATE,
	pt INT DEFAULT 0,
	visited INT DEFAULT 0,
	role_id INT NOT NULL DEFAULT 99,
	use_yn BOOLEAN DEFAULT TRUE
);

CREATE VIEW userList AS(
	SELECT u.user_id AS user_id, u.active AS ACTIVE, u.login_id AS login_id, u.user_name AS user_name, u.password AS PASSWORD, u.use_yn AS use_yn, u.role_id AS role_id, r.role AS roleNm
	FROM user u
	LEFT JOIN role r ON u.role_id = r.role_id
);

CREATE TABLE board_mgn(
	bm_no INT AUTO_INCREMENT PRIMARY KEY,	-- 게시판 번호 : 자동 발생
	board_name VARCHAR(100) NOT NULL,		-- 게시판 이름
	about_auth INT NOT NULL,					-- 게시판 글쓰기, 수정, 삭제 관련 권한
	comment_use BOOLEAN DEFAULT FALSE,		-- 게시판 댓글 사용 유무
	file_use BOOLEAN DEFAULT FALSE			-- 게시판 파일 사용 유무
);

INSERT INTO board_mgn VALUES (DEFAULT, '공지사항', 1, FALSE, FALSE);

CREATE TABLE board(
	bno INT PRIMARY KEY AUTO_INCREMENT,   							-- 게시글 번호 : 자동 발생
	bm_no INT NOT NULL,   												-- 게시판 타입 
	title VARCHAR(200) NOT NULL,   									-- 게시글 제목
	content VARCHAR(5000),   											-- 게시글 내용
	author VARCHAR(20) NOT NULL,   									-- 작성자
	res_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,   	-- 등록일
	visited INT DEFAULT 0   											-- 조회수
);

CREATE VIEW boardList AS (SELECT b.bno AS bno, b.bm_no AS bm_no, b.title AS title, b.content AS content, b.author AS author, b.res_date AS res_date, b.visited as visited, bm.board_name AS board_name, u.user_name AS user_name, bm.about_auth AS about_auth, bm.comment_use AS comment_use, bm.file_use AS file_use FROM board b, user u, board_mgn bm WHERE b.author = u.login_id AND bm.bm_no = b.bm_no order BY b.bno ASC);

CREATE TABLE comment(
   cno INT PRIMARY KEY AUTO_INCREMENT,   							-- 댓글번호: 자동발생
	author VARCHAR(20) NOT NULL,   									-- 댓글 작성자
	content VARCHAR(1000) NOT NULL,   								-- 댓글 내용
	res_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,   	-- 댓글 등록일
	par_no INT NOT NULL   												-- 해당 게시글 번호
);

CREATE VIEW commentList AS (SELECT c.cno AS cno, c.author AS author, c.content AS content, c.res_date AS res_Date, c.par_no AS par_no, u.user_name AS user_name FROM comment c, user u WHERE c.author = u.login_id order BY c.cno ASC);

CREATE TABLE files(
	fno INT PRIMARY KEY AUTO_INCREMENT,   								-- 파일번호: 자동발생
	par_no INT NOT NULL,   													-- 해당 게시글 번호
	save_folder VARCHAR(1000) NOT NULL,									-- 파일 저장 폴더
	origin_name VARCHAR(500) NOT NULL,									-- 파일 원래 이름
	save_name VARCHAR(500) NOT NULL,										-- 파일 저장 이름
	file_type VARCHAR(100) NOT NULL,										-- 파일 확장자
	upload_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,	-- 파일 업로드 일자
	to_use VARCHAR(100) NOT NULL											-- 사용 테이블
);