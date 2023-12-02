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
	user_id BIGINT PRIMARY KEY AUTO_INCREMENT,
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
	role_id INT NOT NULL DEFAULT 99
);

CREATE VIEW userList AS(
	SELECT u.user_id AS user_id, u.active AS ACTIVE, u.login_id AS login_id, u.user_name AS user_name, u.password AS PASSWORD, u.role_id AS role_id, r.role AS roleNm
	FROM user u
	LEFT JOIN role r ON u.role_id = r.role_id
);

CREATE TABLE board_mgn(
	bm_no INT AUTO_INCREMENT PRIMARY KEY,	-- 게시판 번호 : 자동 발생
	board_name VARCHAR(100) NOT NULL,		-- 게시판 이름
	read_auth INT NOT NULL,						-- 게시판 읽기 권한
	about_auth INT NOT NULL,					-- 게시판 글쓰기, 수정, 삭제 관련 권한
	comment_use BOOLEAN DEFAULT FALSE,		-- 게시판 댓글 사용 유무
	file_use BOOLEAN DEFAULT FALSE			-- 게시판 파일 사용 유무
);

INSERT INTO board_mgn VALUES (DEFAULT, '공지사항', 1, FALSE, FALSE, FALSE);

CREATE TABLE board(
	bno INT PRIMARY KEY AUTO_INCREMENT,   							-- 게시글 번호 : 자동 발생
	bm_no INT NOT NULL,   												-- 게시판 타입 
	title VARCHAR(200) NOT NULL,   									-- 게시글 제목
	content VARCHAR(5000),   											-- 게시글 내용
	author BIGINT NOT NULL,   											-- 작성자
	res_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,   	-- 등록일
	visited INT DEFAULT 0   											-- 조회수
);

CREATE VIEW boardList AS (SELECT b.bno AS bno, b.bm_no AS bm_no, b.title AS title, b.content AS content, b.res_date AS res_date, b.visited as visited, bm.board_name AS board_name, b.author AS author, u.user_name AS user_name, bm.about_auth AS about_auth, bm.comment_use AS comment_use, bm.file_use AS file_use FROM board b, user u, board_mgn bm WHERE b.author = u.user_id AND bm.bm_no = b.bm_no order BY b.bno ASC);

CREATE TABLE comment(
   cno INT PRIMARY KEY AUTO_INCREMENT,   							-- 댓글번호: 자동발생
	author BIGINT NOT NULL,   											-- 댓글 작성자
	content VARCHAR(1000) NOT NULL,   								-- 댓글 내용
	res_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,   -- 댓글 등록일
	par_no INT NOT NULL   												-- 해당 게시글 번호
);

CREATE VIEW commentList AS (SELECT c.cno AS cno, c.content AS content, c.res_date AS res_Date, c.par_no AS par_no, c.author AS author, u.user_name AS user_name FROM comment c, user u WHERE c.author = u.user_id order BY c.cno ASC);

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

CREATE TABLE product(
	pro_no INT PRIMARY KEY AUTO_INCREMENT,				-- 상품 번호 
	title VARCHAR(200) NOT NULL,							-- 상품 이름
	pro_price INT NOT NULL,									-- 상품 가격
	content VARCHAR(5000),									-- 상품 설명
	user_id BIGINT NOT NULL,								-- 상품 등록자
	pro_active VARCHAR(30) NOT NULL,						-- 상품 거래 상태 (거래 완료, 미완료)
	reg_date DATETIME DEFAULT CURRENT_TIMESTAMP,		-- 상품 등록일
	location VARCHAR(350) NOT NULL,						-- 상품 판매 지역
	pro_type VARCHAR(30) NOT NULL,						-- 상품 카테고리 (국어, 영어 등)
	pro_state VARCHAR(30) NOT NULL						-- 상품 상태 (최상, 상, 중)
);

CREATE TABLE category (
    category_no VARCHAR(30) PRIMARY KEY, 		-- 카테고리 아이디
    category_name VARCHAR(50) NOT NULL,		-- 카테고리명
    depth_num INT DEFAULT 2,						-- 카테고리 차수
    category_par VARCHAR(25),						-- 카테고리 부모
    priority_num INT DEFAULT 99					-- 우선순위
);

CREATE TABLE chat_room(
	room_id BIGINT PRIMARY KEY AUTO_INCREMENT,
	product_id INT NOT NULL,
	buyer_id BIGINT NOT NULL,
	reg_date DATETIME DEFAULT CURRENT_TIMESTAMP()
);

CREATE VIEW chatRoomView AS (SELECT r.room_id AS room_id, r.product_id AS product_id, p.title AS productName, r.reg_date AS reg_date, r.buyer_id AS buyer_id, u.user_name AS user_name, u.active AS ACTIVE FROM chat_room r LEFT JOIN user u ON r.buyer_id = u.user_id LEFT JOIN product p ON r.product_id = p.pro_no);

CREATE TABLE chat_list(
	chat_id BIGINT PRIMARY KEY AUTO_INCREMENT,
	sender_id BIGINT NOT NULL,
	send_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	message TEXT NOT NULL,
	read_yn BOOLEAN DEFAULT FALSE,
	room_id BIGINT NOT null
);

CREATE VIEW chatListView AS (SELECT r.chat_id AS chat_id, r.send_date AS send_date, r.message AS message, r.read_yn AS read_yn, r.room_id AS room_id, r.sender_id AS sender_id, u.user_name AS user_name FROM chat_list r LEFT JOIN user u ON r.sender_id = u.user_id);

-- 삭제 예정 시작 --
insert into user (ACTIVE, login_id, user_name, password, email, tel, addr1, addr2, postcode, birth, role_id) VALUES (1, 'admin', '관리자', '$2a$10$LEclL83IcxKcJT7/RX34j./XrDz4BudorZpdUqL0giJCChr1Fa5Xy', 'kim@tsherpa.com', '010-8524-2580', '기본주소', '상세주소', '00101', '1990-11-09', 1);
insert into user (ACTIVE, login_id, user_name, password, email, tel, addr1, addr2, postcode, birth, role_id) VALUES (1, 'kim', '김리자', '$2a$10$LEclL83IcxKcJT7/RX34j./XrDz4BudorZpdUqL0giJCChr1Fa5Xy', 'kim@tsherpa.com', '010-8524-2580', '기본주소', '상세주소', '00101', '1990-11-09', 3);
insert into user (ACTIVE, login_id, user_name, password, email, tel, addr1, addr2, postcode, birth, role_id) VALUES (1, 'shin', '신리자', '$2a$10$LEclL83IcxKcJT7/RX34j./XrDz4BudorZpdUqL0giJCChr1Fa5Xy', 'shin@tsherpa.com', '010-8524-2580', '기본주소', '상세주소', '00101', '1990-11-09', 99);
insert into user (ACTIVE, login_id, user_name, password, email, tel, addr1, addr2, postcode, birth, role_id) VALUES (1, 'park', '박리자', '$2a$10$LEclL83IcxKcJT7/RX34j./XrDz4BudorZpdUqL0giJCChr1Fa5Xy', 'park@tsherpa.com', '010-8524-2580', '기본주소', '상세주소', '00101', '1990-11-09', 99);

INSERT INTO product VALUES(DEFAULT, '테스트상품', 50000, '테스트 상품 관련 내역입니다.', 2, 'trade01', DEFAULT, '가산동', 'product01', 'state01');

INSERT INTO category VALUES('trade', '거래', 1, '', DEFAULT);
INSERT INTO category VALUES('trade01', '미완료', 2, 'trade', DEFAULT);
INSERT INTO category VALUES('trade02', '예약', 2, 'trade', DEFAULT);
INSERT INTO category VALUES('trade03', '완료', 2, 'trade', DEFAULT);
INSERT INTO category VALUES('product', '상품', 1, '', DEFAULT);
INSERT INTO category VALUES('product01', '국어', 2, 'product', DEFAULT);
INSERT INTO category VALUES('product02', '영어', 2, 'product', DEFAULT);
INSERT INTO category VALUES('state', '상태', 1, '', DEFAULT);
INSERT INTO category VALUES('state01', '중', 2, 'state', DEFAULT);
INSERT INTO category VALUES('state02', '상', 2, 'state', DEFAULT);
INSERT INTO category VALUES('state03', '최상', 2, 'state', DEFAULT);
-- 삭제 예정 종료 --

-- 미진행 시작 --
CREATE TABLE product_likes(
	user_id BIGINT NOT NULL,      							-- 사용자 ID
   pro_no INT NOT NULL,           							-- 상품 번호
   res_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP, 		-- 좋아요를 누른 시간
   PRIMARY KEY (user_id, pro_no)
);

CREATE TABLE trade(
	tno INT AUTO_INCREMENT primary KEY,					-- 거래 번호
	pro_no INT NOT NULL,										-- 상품 번호
	buy_user_id BIGINT NOT NULL,							-- 구매자 아이디
	res_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,	-- 거래 일시
	finish_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP	-- 완료 일시
);

-- 미진행 종료 --