package efub.assignment.community.messageRoom.dto;

import lombok.*;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
@AllArgsConstructor
public class MessageRoomListDto {
    private List<MessageRoomDto> list;
}
