package efub.assignment.community.message.domain;

import efub.assignment.community.member.domain.Member;
import efub.assignment.community.messageRoom.domain.MessageRoom;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@EntityListeners(AuditingEntityListener.class)
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_id", updatable = false)
    private Long messageId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "messageRoom_id", updatable = false, nullable = false)
    private MessageRoom messageRoom;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id", updatable = false, nullable = false)
    private Member sender;

    @Column(name = "content", updatable = false, nullable = false, length = 1000)
    private String content;

    @CreatedDate
    @Column(name = "sending_time", updatable = false)
    private LocalDateTime sendingTime;

    @Builder
    public Message(MessageRoom messageRoom, Member sender, String content){
        this.messageRoom = messageRoom;
        this.sender = sender;
        this.content = content;
    }
}
