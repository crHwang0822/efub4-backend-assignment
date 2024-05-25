package efub.assignment.community.member.controller;

import efub.assignment.community.message.dto.MessageListDto;
import efub.assignment.community.message.service.MessageService;
import efub.assignment.community.messageRoom.domain.MessageRoom;
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
    private final MessageService messageService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public MessageRoomListDto getMessageRoomList(@PathVariable("memberId") final Long memberId){
        return messageRoomService.getMessageRoomList(memberId);
    }

    @GetMapping("/{messageRoomId}/messages")
    @ResponseStatus(HttpStatus.OK)
    public MessageListDto getMessageList(@PathVariable("memberId") final Long memberId, @PathVariable("messageRoomId") final Long messageRoomId){
        MessageRoom messageRoom = messageRoomService.findMessageRoomById(messageRoomId);
        return messageService.getMessageList(memberId,messageRoom);
    }


}
