package efub.assignment.community.messageRoom.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class MessageRoomExistsRequestDto {
    @NotNull
    private Long senderId;
    @NotNull
    private Long receiverId;
    @NotNull
    private Long postId;
}
