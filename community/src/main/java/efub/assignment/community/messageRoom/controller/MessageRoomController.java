package efub.assignment.community.messageRoom.controller;

import efub.assignment.community.messageRoom.dto.MessageRoomCreateRequestDto;
import efub.assignment.community.messageRoom.dto.MessageRoomCreateResponseDto;
import efub.assignment.community.messageRoom.dto.MessageRoomExistsRequestDto;
import efub.assignment.community.messageRoom.service.MessageRoomService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/messageRooms")
public class MessageRoomController {

    private final MessageRoomService messageRoomService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MessageRoomCreateResponseDto createMessageRoom(@RequestBody @Valid MessageRoomCreateRequestDto requestDto){
        return messageRoomService.createMessageRoom(requestDto);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Long getMessageRoomExists(@RequestBody @Valid MessageRoomExistsRequestDto requestDto){
        return messageRoomService.getMessageRoomExists(requestDto);
    }

}
