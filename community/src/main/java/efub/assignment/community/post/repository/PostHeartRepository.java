package efub.assignment.community.post.repository;

import efub.assignment.community.member.domain.Member;
import efub.assignment.community.post.domain.Post;
import efub.assignment.community.post.domain.PostHeart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostHeartRepository extends JpaRepository<PostHeart, Long> {
    boolean existsByPostAndMember(Post post, Member member);
    Optional<PostHeart> findByPostAndMember(Post post, Member member);
}
