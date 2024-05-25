package efub.assignment.community.message.controller;

import efub.assignment.community.message.dto.MessageCreateRequestDto;
import efub.assignment.community.message.dto.MessageCreateResponseDto;
import efub.assignment.community.message.service.MessageService;
import efub.assignment.community.messageRoom.domain.MessageRoom;
import efub.assignment.community.messageRoom.service.MessageRoomService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/messages")
@RequiredArgsConstructor
public class MessageController {
    private final MessageService messageService;
    private final MessageRoomService messageRoomService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public MessageCreateResponseDto createMessage(@RequestBody @Valid MessageCreateRequestDto requestDto){
        MessageRoom messageRoom = messageRoomService.findMessageRoomById(requestDto.getMessageRoomId());
        return messageService.createMessage(requestDto, messageRoom);
    }
}
