package efub.assignment.community.post.domain;

import efub.assignment.community.board.domain.Board;
import efub.assignment.community.member.domain.Member;
import efub.assignment.community.post.repository.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE,
        connection = EmbeddedDatabaseConnection.H2)
class PostTest {

    @Autowired
    TestEntityManager testEntityManager;

    static Member member;
    static Board board;

    @BeforeEach
    void setup(){
        member = Member.builder()
                .email("cococofls@ewhain.net")
                .nickname("채린")
                .password("chaerinPassword")
                .university("이화여자대학교")
                .studentId("2176429")
                .build();

        testEntityManager.persist(member);

        board = Board.builder()
                .member(member)
                .name("테스트용 게시판")
                .description("설명")
                .build();

        testEntityManager.persist(board);
    }

    @Test
    @DisplayName("Post 생성 테스트")
    void createPostEntity(){
        //given
        boolean anonymous = true;
        String content = "글 내용";

        //when
        Post post = Post.builder()
                .board(board)
                .member(member)
                .anonymous(anonymous)
                .content(content)
                .build();

        //then
        assertEquals(anonymous,post.isAnonymous());
        assertEquals(content,post.getContent());
        assertEquals(board,post.getBoard());
        assertEquals(member,post.getMember());
    }

    @Test
    @DisplayName("Post 내용이 1000자가 넘는 경우 테스트")
    void createPostEntity_Fail_WhenContentIsTooLong(){
        //given
        boolean anonymous = true;
        String content = "1111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111";

        Post post = Post.builder()
                .board(board)
                .member(member)
                .anonymous(anonymous)
                .content(content)
                .build();

        //when & then
        testEntityManager.persist(post);
    }

    @Test
    @DisplayName("Post 내용 변경 테스트")
    void updatePostContent(){
        //given
        boolean anonymous = true;
        String content = "글 내용";

        Post post = Post.builder()
                .board(board)
                .member(member)
                .anonymous(anonymous)
                .content(content)
                .build();

        String newContent = "글 새 내용";

        //when
        post.updateContent(newContent);

        //then
        assertEquals(newContent,post.getContent());
    }




}