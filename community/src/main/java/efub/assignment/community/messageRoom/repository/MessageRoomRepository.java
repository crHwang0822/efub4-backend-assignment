package efub.assignment.community.messageRoom.repository;

import efub.assignment.community.member.domain.Member;
import efub.assignment.community.messageRoom.domain.MessageRoom;
import efub.assignment.community.post.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MessageRoomRepository extends JpaRepository<MessageRoom, Long> {
    public Optional<MessageRoom> findByPostAndSenderAndReceiver(Post post, Member sender, Member receiver);
    @Query("SELECT mr FROM MessageRoom mr WHERE mr.sender = :member OR mr.receiver = :member")
    List<MessageRoom> findBySenderOrReceiver(Member member);
}
