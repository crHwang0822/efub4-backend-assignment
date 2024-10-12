
CREATE TABLE IF NOT EXISTS member (
    member_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(60) NOT NULL,
    password VARCHAR NOT NULL,
    nickname VARCHAR(20) NOT NULL,
    university VARCHAR(20) NOT NULL,
    student_id VARCHAR(10) NOT NULL,
    status ENUM('REGISTERED', 'UNREGISTERED')
);
INSERT INTO member(email,password,nickname,university,student_id,status) VALUES
    ('test1@test.com', 'testPW123', 'test1', 'testUni', '1234567', 'REGISTERED'),
    ('test2@test.com', 'testPW123', 'test2', 'testUni', '1234568', 'REGISTERED');

CREATE TABLE IF NOT EXISTS board (
                                     board_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                     member_id BIGINT,
                                     name VARCHAR(20) NOT NULL,
    description VARCHAR(100) NOT NULL,
    notice VARCHAR(1000),
    FOREIGN KEY (member_id) REFERENCES member(member_id) ON DELETE CASCADE
    );
INSERT INTO board(member_id, name, description) VALUES
    (1,'testBoard', 'testDescription');

CREATE TABLE IF NOT EXISTS post (
                                    post_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                    board_id BIGINT,
                                    member_id BIGINT,
                                    is_anonymous TINYINT(1) NOT NULL,
    content VARCHAR(1000),
    FOREIGN KEY (board_id) REFERENCES board(board_id) ON DELETE CASCADE,
    FOREIGN KEY (member_id) REFERENCES member(member_id) ON DELETE CASCADE
    );
INSERT INTO post(board_id, member_id, is_anonymous, content) VALUES
    (1,2,1,'test'),
    (1,2,0,'노릇노릇'),
    (1,1,0,'한강');