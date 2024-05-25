package efub.assignment.community.message.repository;

import efub.assignment.community.member.domain.Member;
import efub.assignment.community.message.domain.Message;
import efub.assignment.community.messageRoom.domain.MessageRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MessageRepository extends JpaRepository<Message, Long> {
    Optional<Message> findFirstByMessageRoomOrderBySendingTimeDesc(MessageRoom messageRoom);
    List<Message> findAllByMessageRoomAndSender(MessageRoom messageRoom, Member sender);
}
