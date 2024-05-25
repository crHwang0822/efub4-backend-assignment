package efub.assignment.community.message.service;

import efub.assignment.community.member.domain.Member;
import efub.assignment.community.member.service.MemberService;
import efub.assignment.community.message.domain.Message;
import efub.assignment.community.message.dto.MessageCreateRequestDto;
import efub.assignment.community.message.dto.MessageCreateResponseDto;
import efub.assignment.community.message.repository.MessageRepository;
import efub.assignment.community.messageRoom.domain.MessageRoom;
import efub.assignment.community.messageRoom.dto.MessageRoomDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final MessageRepository messageRepository;
    private final MemberService memberService;

    public MessageCreateResponseDto createMessage(MessageCreateRequestDto requestDto, MessageRoom messageRoom){
        Member sender = memberService.findMemberById(requestDto.getSenderId());
        if(!(sender.equals(messageRoom.getSender())||sender.equals(messageRoom.getReceiver()))){
            throw new IllegalArgumentException("쪽지를 보낼 권한이 없습니다.");
        }
        Message message = Message.builder()
                .messageRoom(messageRoom)
                .sender(sender)
                .content(requestDto.getContent())
                .build();
        messageRepository.save(message);
        return message.toCreateResponseDto();
    }

    public void createFirstMessage(final MessageRoom messageRoom){
        Message message = Message.builder()
                .messageRoom(messageRoom)
                .sender(messageRoom.getSender())
                .content(messageRoom.getContent())
                .build();
        messageRepository.save(message);
    }

    public List<MessageRoomDto> findRecentMessageByMessageRoomId(List<MessageRoom> messageRoomList) {
        List<Message> recentMessages = messageRoomList.stream()
                .map(messageRoom -> messageRepository
                        .findFirstByMessageRoomOrderBySendingTimeDesc(messageRoom)
                        .orElseThrow(() -> new RuntimeException(messageRoom.getMessageRoomId() + ": 해당 쪽지방에 대한 쪽지를 찾을 수 없습니다.")))
                .collect(Collectors.toList());

        List<MessageRoomDto> messageRoomDtoList = recentMessages.stream()
                .map(recentMessage -> MessageRoomDto.builder()
                        .messageRoomId(recentMessage.getMessageRoom().getMessageRoomId())
                        .recentMessage(recentMessage.getContent())
                        .recentSendingTime(recentMessage.getSendingTime())
                        .build())
                .collect(Collectors.toList());

        return messageRoomDtoList;
    }


}
