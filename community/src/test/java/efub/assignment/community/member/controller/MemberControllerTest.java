package efub.assignment.community.member.controller;

import static org.junit.jupiter.api.Assertions.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import efub.assignment.community.CommunityApplication;
import efub.assignment.community.member.domain.Member;
import efub.assignment.community.member.dto.MemberRequestDto;
import efub.assignment.community.member.repository.MemberRepository;
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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@Sql(scripts = "/data.sql")
@ActiveProfiles("test")
@ContextConfiguration(classes = CommunityApplication.class)
@TestPropertySource(locations = "classpath:application-test.yml")
class MemberControllerTest {

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
    @DisplayName("회원가입 성공")
    void signUp_success() throws Exception {

        //given
        final String url = "/members";
        final String email = "hi@test.com";
        final String password = "password";
        final String nickname = "nickname";
        final String university = "university";
        final String studentId = "1234";
        final MemberRequestDto requestDto = MemberRequestDto.builder()
            .email(email)
            .password(password)
            .nickname(nickname)
            .university(university)
            .studentId(studentId)
            .build();

        //when
        final String requestBody = objectMapper.writeValueAsString(requestDto);

        ResultActions resultActions = mockMvc.perform(post(url)
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody));

        //then
        resultActions
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.memberId").isNotEmpty())
            .andExpect(jsonPath("$.email").value(email))
            .andExpect(jsonPath("$.nickname").value(nickname))
            .andExpect(jsonPath("$.university").value(university))
            .andExpect(jsonPath("$.studentId").value(studentId));
    }

    @Test
    @DisplayName("이미 가입된 이메일로 회원가입 시 실패")
    void signUp_fail() throws Exception {

        //given
        final String url = "/members";
        final String email = "test1@test.com";
        final String password = "password";
        final String nickname = "nickname";
        final String university = "university";
        final String studentId = "1234";
        final MemberRequestDto requestDto = MemberRequestDto.builder()
            .email(email)
            .password(password)
            .nickname(nickname)
            .university(university)
            .studentId(studentId)
            .build();

        //when
        final String requestBody = objectMapper.writeValueAsString(requestDto);

        ResultActions resultActions = mockMvc.perform(post(url)
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody));

        //then
        resultActions
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.memberId").isNotEmpty())
            .andExpect(jsonPath("$.email").value(email))
            .andExpect(jsonPath("$.nickname").value(nickname))
            .andExpect(jsonPath("$.university").value(university))
            .andExpect(jsonPath("$.studentId").value(studentId));
    }

    @Test
    @DisplayName("멤버 조회 성공")
    void getMember_success() throws Exception {

        //given
        final Long memberId = 1L;
        final String url = "/members/{memberId}";

        //when
        ResultActions resultActions = mockMvc.perform(get(url, memberId));
        Member member = memberRepository.findById(memberId).get();

        //then
        resultActions
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.memberId").value(memberId))
            .andExpect(jsonPath("$.email").value(member.getEmail()))
            .andExpect(jsonPath("$.nickname").value(member.getNickname()))
            .andExpect(jsonPath("$.university").value(member.getUniversity()))
            .andExpect(jsonPath("$.studentId").value(member.getStudentId()));
    }

    @Test
    @DisplayName("존재하지 않는 ID로 멤버 조회 시 실패")
    void getMember_fail() throws Exception {

        //given
        final Long memberId = 3L;
        final String url = "/members/{memberId}";

        //when
        ResultActions resultActions = mockMvc.perform(get(url, memberId));

        //then
        resultActions
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.memberId").value(memberId));
    }

    @Test
    @DisplayName("멤버 수정 성공")
    void updateMember_success() throws Exception {

        //given
        final Long memberId = 1L;
        final String url = "/members/profile/{memberId}";
        final String email = "test1@test.com";
        final String password = "password";
        final String nickname = "new nickname";
        final String university = "university";
        final String studentId = "1234";
        final MemberRequestDto requestDto = MemberRequestDto.builder()
            .email(email)
            .password(password)
            .nickname(nickname)
            .university(university)
            .studentId(studentId)
            .build();

        //when
        final String requestBody = objectMapper.writeValueAsString(requestDto);

        ResultActions resultActions = mockMvc.perform(patch(url, memberId)
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody));

        //then
        resultActions
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.nickname").value(nickname));
    }

    @Test
    @DisplayName("이미 존재하는 닉네임으로 멤버 수정 시 실패")
    void updateMember_fail() throws Exception {

        //given
        final Long memberId = 1L;
        final String url = "/members/profile/{memberId}";
        final String email = "test1@test.com";
        final String password = "password";
        final String nickname = "test2";
        final String university = "university";
        final String studentId = "1234";
        final MemberRequestDto requestDto = MemberRequestDto.builder()
            .email(email)
            .password(password)
            .nickname(nickname)
            .university(university)
            .studentId(studentId)
            .build();

        //when
        final String requestBody = objectMapper.writeValueAsString(requestDto);

        ResultActions resultActions = mockMvc.perform(patch(url, memberId)
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody));

        //then
        resultActions
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.nickname").value(nickname));
    }

    @Test
    @DisplayName("멤버 탈퇴 성공")
    void withdraw_success() throws Exception {

        //given
        final Long memberId = 1L;
        final String url = "/members/{memberId}";

        //when
        ResultActions resultActions = mockMvc.perform(patch(url, memberId));

        //then
        resultActions
            .andExpect(status().isOk())
            .andExpect(content().string("삭제가 완료되었습니다."));
    }

    @Test
    @DisplayName("존재하지 않는 ID로 멤버 탈퇴 시 실패")
    void withdraw_fail() throws Exception {

        //given
        final Long memberId = 333L;
        final String url = "/members/{memberId}";

        //when
        ResultActions resultActions = mockMvc.perform(patch(url, memberId));

        //then
        resultActions
            .andExpect(status().isOk())
            .andExpect(content().string("삭제가 완료되었습니다."));
    }
}