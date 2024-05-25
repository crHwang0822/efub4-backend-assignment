package efub.assignment.community.message.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class MessageCreateRequestDto {
    private Long messageRoomId;
    private Long senderId;
    private String content;
}
