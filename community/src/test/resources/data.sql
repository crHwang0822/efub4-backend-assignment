
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

