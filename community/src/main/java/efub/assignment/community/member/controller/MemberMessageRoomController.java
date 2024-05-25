package efub.assignment.community.member.controller;

import efub.assignment.community.messageRoom.dto.MessageRoomListDto;
import efub.assignment.community.messageRoom.service.MessageRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/members/{memberId}/messageRooms")
@RequiredArgsConstructor
public class MemberMessageRoomController {

    private final MessageRoomService messageRoomService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public MessageRoomListDto getMessageRoomList(@PathVariable("memberId") final Long memberId){
        return messageRoomService.getMessageRoomList(memberId);
    }

}
