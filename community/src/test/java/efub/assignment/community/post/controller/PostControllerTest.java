package efub.assignment.community.post.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import efub.assignment.community.CommunityApplication;
import efub.assignment.community.member.repository.MemberRepository;
import efub.assignment.community.post.dto.PostCreateRequestDto;
import efub.assignment.community.post.dto.PostUpdateRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
@AutoConfigureMockMvc
@Sql(scripts = "/data.sql", executionPhase = ExecutionPhase.BEFORE_TEST_CLASS)
@ActiveProfiles("test")
@ContextConfiguration(classes = CommunityApplication.class)
@TestPropertySource(locations = "classpath:application-test.yml")
class PostControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected WebApplicationContext context;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    protected MemberRepository memberRepository;

    @BeforeEach
    public void setMockMvc() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
            .build();
    }

    @Test
    @DisplayName("게시글 생성 성공")
    void createPost_success() throws Exception {

        //given
        final String url = "/posts";
        final Long boardId = 1L;
        final String writerName = "test1";
        final boolean anonymous = true;
        final String content = "내용";
        final PostCreateRequestDto requestDto = PostCreateRequestDto.builder()
            .boardId(boardId)
            .writerName(writerName)
            .anonymous(anonymous)
            .content(content)
            .build();

        //when
        final String requestBody = objectMapper.writeValueAsString(requestDto);

        ResultActions resultActions = mockMvc.perform(post(url)
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody));

        //then
        resultActions
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.boardId").value(boardId))
            .andExpect(jsonPath("$.writerName").value(writerName))
            .andExpect(jsonPath("$.anonymous").value(anonymous))
            .andExpect(jsonPath("$.content").value(content));
    }

    @Test
    @DisplayName("작성자의 닉네임이 DB에 존재하지 않을 경우 게시글 생성 실패")
    void createPost_fail() throws Exception {

        //given
        final String url = "/posts";
        final Long boardId = 1L;
        final String writerName = "spy";
        final boolean anonymous = true;
        final String content = "내용";
        final PostCreateRequestDto requestDto = PostCreateRequestDto.builder()
            .boardId(boardId)
            .writerName(writerName)
            .anonymous(anonymous)
            .content(content)
            .build();

        //when
        final String requestBody = objectMapper.writeValueAsString(requestDto);

        ResultActions resultActions = mockMvc.perform(post(url)
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody));

        //then
        resultActions
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.boardId").value(boardId))
            .andExpect(jsonPath("$.writerName").value(writerName))
            .andExpect(jsonPath("$.anonymous").value(anonymous))
            .andExpect(jsonPath("$.content").value(content));
    }

    @Test
    @DisplayName("게시글 수정 성공")
    void updatePost_success() throws Exception {

        //given
        final Long postId = 1L;
        final String url = "/posts/{postId}";
        final String content = "new content";
        final PostUpdateRequestDto requestDto = PostUpdateRequestDto.builder()
            .content(content)
            .build();

        //when
        final String requestBody = objectMapper.writeValueAsString(requestDto);

        ResultActions resultActions = mockMvc.perform(patch(url, postId)
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody));

        //then
        resultActions
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.content").value(content));
    }

    @Test
    @DisplayName("존재하지 않는 게시글 ID로 게시글 수정 시 실패")
    void updatePost_fail() throws Exception {

        //given
        final Long postId = 1000L;
        final String url = "/posts/{postId}";
        final String content = "new content";
        final PostUpdateRequestDto requestDto = PostUpdateRequestDto.builder()
            .content(content)
            .build();

        //when
        final String requestBody = objectMapper.writeValueAsString(requestDto);

        ResultActions resultActions = mockMvc.perform(patch(url, postId)
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody));

        //then
        resultActions
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.content").value(content));
    }

    @Test
    @DisplayName("게시글 목록 조회 성공")
    void getAllPosts_success() throws Exception {

        //given
        final Long boardId = 1L;
        final String url = "/posts/list/{boardId}";

        //when
        ResultActions resultActions = mockMvc.perform(get(url, boardId));

        //then
        resultActions
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.count").value(3));
    }

    @Test
    @DisplayName("게시글 상세 조회 성공")
    void getPost_success() throws Exception {

        //given
        final Long postId = 2L;
        final String url = "/posts/{postId}";

        //when
        ResultActions resultActions = mockMvc.perform(get(url, postId));

        //then
        resultActions
            .andExpect(status().isOk())
            //조회한 결과가 data.sql에서 저장한 데이터와 일치하는지 확인
            .andExpect(jsonPath("$.boardId").value(1))
            .andExpect(jsonPath("$.writerName").value("test2"))
            .andExpect(jsonPath("$.anonymous").value(false))
            .andExpect(jsonPath("$.content").value("노릇노릇"));
    }

    @Test
    @DisplayName("존재하지 않는 게시글 ID로 게시글 상세 조회 시 실패")
    void getPost_fail() throws Exception {

        //given
        final Long postId = 100L;
        final String url = "/posts/{postId}";

        //when
        ResultActions resultActions = mockMvc.perform(get(url, postId));

        //then
        resultActions
            .andExpect(status().isOk())
            //조회한 결과가 data.sql에서 저장한 데이터와 일치하는지 확인
            .andExpect(jsonPath("$.boardId").value(1))
            .andExpect(jsonPath("$.writerName").value("test2"))
            .andExpect(jsonPath("$.anonymous").value(false))
            .andExpect(jsonPath("$.content").value("노릇노릇"));
    }

    @Test
    @DisplayName("게시글 삭제 성공")
    void deletePost_success() throws Exception {

        //given
        final Long postId = 3L;
        final String url = "/posts/{postId}";

        //when
        ResultActions resultActions = mockMvc.perform(delete(url, postId));

        //then
        resultActions
            .andExpect(status().isOk())
            .andExpect(content().string("게시글을 삭제했습니다."));
    }
    
    @Test
    @DisplayName("존재하지 않는 게시글 ID로 게시글 삭제 시 실패")
    void deletePost_fail() throws Exception {

        //given
        final Long postId = 100L;
        final String url = "/posts/{postId}";

        //when
        ResultActions resultActions = mockMvc.perform(delete(url, postId));

        //then
        resultActions
            .andExpect(status().isOk())
            .andExpect(content().string("게시글을 삭제했습니다."));
    }
}