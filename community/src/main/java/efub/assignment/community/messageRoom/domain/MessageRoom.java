package efub.assignment.community.messageRoom.domain;

import efub.assignment.community.global.entity.BaseTimeEntity;
import efub.assignment.community.member.domain.Member;
import efub.assignment.community.messageRoom.dto.MessageRoomCreateResponseDto;
import efub.assignment.community.post.domain.Post;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class MessageRoom extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_room_id", updatable = false)
    private Long messageRoomId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", updatable = false, nullable = false)
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id", updatable = false, nullable = false)
    private Member sender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id", updatable = false, nullable = false)
    private Member receiver;

    @Column(name = "first_message", updatable = false, nullable = false, length = 1000)
    private String content;

    @Builder
    public MessageRoom(Post post, Member sender, Member receiver, String content){
        this.post = post;
        this.sender = sender;
        this.receiver = receiver;
        this.content = content;
    }

    public MessageRoomCreateResponseDto toCreateResponseDto(){
        return MessageRoomCreateResponseDto.builder()
                .messageRoomId(this.messageRoomId)
                .senderId(this.sender.getMemberId())
                .receiverId(this.receiver.getMemberId())
                .content(this.content)
                .sendingTime(this.getRegDate())
                .build();
    }
}
