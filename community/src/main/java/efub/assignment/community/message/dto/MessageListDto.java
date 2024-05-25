package efub.assignment.community.message.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
@AllArgsConstructor
public class MessageListDto {
    private Long messageRoomId;
    private Long partnerId;
    private List<MessageDto> messages;
}
