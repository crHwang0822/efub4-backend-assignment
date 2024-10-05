package efub.assignment.community.member.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MemberTest {

    @Test
    @DisplayName("Member 생성 테스트 성공")
    void createMemberEntity(){
        //given
        String email = "cococofls@ewhain.net";
        String nickname = "채린";
        String password = "chaerinPassword";
        String university = "이화여자대학교";
        String studentId = "2176429";

        //when
        Member member = Member.builder()
                .email(email)
                .nickname(nickname)
                .password(password)
                .university(university)
                .studentId(studentId)
                .build();

        //then
        assertEquals(email,member.getEmail());
        assertEquals(nickname,member.getNickname());
        assertEquals(password,member.getPassword());
        assertEquals(university,member.getUniversity());
        assertEquals(studentId,member.getStudentId());
    }

    @Test
    @DisplayName("닉네임 변경 테스트 성공")
    void changeNickname(){
        //given
        String email = "cococofls@ewhain.net";
        String nickname = "채린";
        String password = "chaerinPassword";
        String university = "이화여자대학교";
        String studentId = "2176429";

        Member member = Member.builder()
                .email(email)
                .nickname(nickname)
                .password(password)
                .university(university)
                .studentId(studentId)
                .build();

        String newNickname = "새채린";

        //when
        member.changeNickname(newNickname);

        //then
        assertEquals(newNickname,member.getNickname());
    }

    @Test
    @DisplayName("계정 비활성화 테스트 실패")
    void deactivateAccount_Fail(){
        //given
        String email = "cococofls@ewhain.net";
        String nickname = "채린";
        String password = "chaerinPassword";
        String university = "이화여자대학교";
        String studentId = "2176429";

        Member member = Member.builder()
                .email(email)
                .nickname(nickname)
                .password(password)
                .university(university)
                .studentId(studentId)
                .build();

        //when
        member.deactivateAccount();

        //then
        assertEquals(MemberStatus.REGISTERED,member.getStatus(),"멤버 상태는 UNREGISTERED 여야 합니다.");
    }
    

}