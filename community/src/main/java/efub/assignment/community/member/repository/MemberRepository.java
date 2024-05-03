package efub.assignment.community.member.repository;

import efub.assignment.community.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member,Long> {
    Boolean existsByEmail(String email);
    Boolean existsByNickname(String nickname);
    Optional<Member> findByNickname(String nickname);
}
