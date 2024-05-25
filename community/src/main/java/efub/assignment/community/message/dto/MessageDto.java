package efub.assignment.community.message.dto;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MessageDto{
    private String content;
    private LocalDateTime sendingTime;
    private boolean hasSent;
}