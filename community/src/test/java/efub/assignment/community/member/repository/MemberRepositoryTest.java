package efub.assignment.community.member.repository;

import efub.assignment.community.member.domain.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE,
    connection = EmbeddedDatabaseConnection.H2)
public class MemberRepositoryTest {

    @Autowired
    private TestEntityManager testEntityManager;
    @Autowired
    private MemberRepository memberRepository;

    @Test
    @DisplayName("멤버 저장 테스트")
    void saveMember(){
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
        testEntityManager.persist(member);

        //then
        assertEquals(member,testEntityManager.find(Member.class,member.getMemberId()));
    }

    @Test
    @DisplayName("닉네임으로 멤버 조회 테스트")
    void searchMemberByNickname(){
        //given
        String email = "cococofls@ewhain.net";
        String nickname = "채린";
        String password = "chaerinPassword";
        String university = "이화여자대학교";
        String studentId = "2176429";

        Member member1 = Member.builder()
                .email(email)
                .nickname(nickname)
                .password(password)
                .university(university)
                .studentId(studentId)
                .build();

        testEntityManager.persist(member1);

        //when
        Member member2 = memberRepository.findByNickname(nickname).get();

        //then
        assertEquals(member1,member2);
    }

    @Test
    @DisplayName("이메일로 멤버 존재 여부 확인 테스트")
    void checkMemberExistsByEmail(){
        //given
        String email = "cococofls@ewhain.net";
        String nickname = "채린";
        String password = "chaerinPassword";
        String university = "이화여자대학교";
        String studentId = "2176429";

        Member member1 = Member.builder()
                .email(email)
                .nickname(nickname)
                .password(password)
                .university(university)
                .studentId(studentId)
                .build();

        testEntityManager.persist(member1);

        //when & then
        assertTrue(memberRepository.existsByEmail(email));
    }

    @Test
    @DisplayName("이메일로 멤버 존재 여부 확인 테스트 실패")
    void checkMemberExistsByEmail_Fail(){
        //given
        String email = "cococofls@ewhain.net";
        String nickname = "채린";
        String password = "chaerinPassword";
        String university = "이화여자대학교";
        String studentId = "2176429";

        Member member1 = Member.builder()
                .email(email)
                .nickname(nickname)
                .password(password)
                .university(university)
                .studentId(studentId)
                .build();

        testEntityManager.persist(member1);

        //when & then
        assertTrue(memberRepository.existsByEmail("없는 이메일"));
    }


}

