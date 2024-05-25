package efub.assignment.community.messageRoom.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class MessageRoomCreateRequestDto {
    @NotNull
    private Long postId;
    @NotNull
    private Long senderId;
    @NotNull
    private Long receiverId;
    @NotBlank
    private String content;
}
