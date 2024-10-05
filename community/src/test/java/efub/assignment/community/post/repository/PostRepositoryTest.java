package efub.assignment.community.post.repository;

import efub.assignment.community.board.domain.Board;
import efub.assignment.community.member.domain.Member;
import efub.assignment.community.post.domain.Post;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE,
        connection = EmbeddedDatabaseConnection.H2)
public class PostRepositoryTest {

    @Autowired
    TestEntityManager testEntityManager;
    @Autowired
    PostRepository postRepository;

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
    @DisplayName("Post 저장 테스트")
    void savePost(){
        //given
        boolean anonymous = true;
        String content = "글 내용";

        Post post = Post.builder()
                .board(board)
                .member(member)
                .anonymous(anonymous)
                .content(content)
                .build();

        //when
        testEntityManager.persist(post);

        //then
        assertEquals(post,testEntityManager.find(Post.class,post.getPostId()));
    }

    @Test
    @DisplayName("BoardId로 Post 목록 조회 테스트")
    void searchPostListByBoardId(){
        //given
        boolean anonymous = true;
        String content = "글 내용";

        Post post1 = Post.builder()
                .board(board)
                .member(member)
                .anonymous(anonymous)
                .content(content)
                .build();

        Post post2 = Post.builder()
                .board(board)
                .member(member)
                .anonymous(anonymous)
                .content(content)
                .build();

        testEntityManager.persist(post1);
        testEntityManager.persist(post2);

        //when
        List<Post> posts = postRepository.findByBoard_BoardId(board.getBoardId());

        //then
        assertEquals(post1,posts.get(0));
        assertEquals(post2,posts.get(1));
    }

    @Test
    @DisplayName("Post 조회 테스트 실패")
    void deletePost_Fail(){
        //given
        boolean anonymous = true;
        String content = "글 내용";

        Post post = Post.builder()
                .board(board)
                .member(member)
                .anonymous(anonymous)
                .content(content)
                .build();

        testEntityManager.persist(post);

        //when & then
        assertTrue(postRepository.existsById((long)1234));
    }


}
