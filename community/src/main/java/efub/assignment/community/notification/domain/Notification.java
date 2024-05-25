package efub.assignment.community.notification.domain;

import efub.assignment.community.member.domain.Member;
import efub.assignment.community.notification.dto.NotificationDto;
import efub.assignment.community.post.domain.Post;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@EntityListeners(AuditingEntityListener.class)
@Builder
@AllArgsConstructor
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_id", updatable = false)
    private Long notificationId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", updatable = false,nullable = false)
    private Member member;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", updatable = false, nullable = false)
    private NotificationType type;

    @Column(name = "content", updatable = false, nullable = false, length = 1000)
    private String content;

    @CreatedDate
    @Column(name = "sending_time", updatable = false)
    private LocalDateTime sendingTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    public NotificationDto toDto(){

        if(this.post!=null){
            return NotificationDto.builder()
                    .boardName(this.post.getBoard().getName())
                    .type(this.type.getType())
                    .content(this.content)
                    .time(this.sendingTime)
                    .build();
        }

        return NotificationDto.builder()
                .type(this.type.getType())
                .content(this.content)
                .time(this.sendingTime)
                .build();
    }

}
