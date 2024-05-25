package efub.assignment.community.notification.dto;

import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
@AllArgsConstructor
public class NotificationDto {
    private String boardName;
    private String type;
    private String content;
    private LocalDateTime time;
}
