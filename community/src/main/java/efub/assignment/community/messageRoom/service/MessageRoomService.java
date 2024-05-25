package efub.assignment.community.messageRoom.service;

import efub.assignment.community.member.domain.Member;
import efub.assignment.community.member.service.MemberService;
import efub.assignment.community.message.service.MessageService;
import efub.assignment.community.messageRoom.domain.MessageRoom;
import efub.assignment.community.messageRoom.dto.*;
import efub.assignment.community.messageRoom.repository.MessageRoomRepository;
import efub.assignment.community.post.domain.Post;
import efub.assignment.community.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class MessageRoomService {

    private final MessageRoomRepository messageRoomRepository;
    private final MessageService messageService;
    private final PostService postService;
    private final MemberService memberService;

    public MessageRoomCreateResponseDto createMessageRoom(MessageRoomCreateRequestDto requestDto){
        Post post = postService.findPostById(requestDto.getPostId());
        Member sender = memberService.findMemberById(requestDto.getSenderId());
        Member receiver = memberService.findMemberById(requestDto.getReceiverId());
        MessageRoom messageRoom = MessageRoom.builder()
                .post(post)
                .sender(sender)
                .receiver(receiver)
                .content(requestDto.getContent())
                .build();
        messageRoomRepository.save(messageRoom);

        //첫 쪽지 생성
        messageService.createFirstMessage(messageRoom);

        return messageRoom.toCreateResponseDto();
    }

    public Long getMessageRoomExists(MessageRoomExistsRequestDto requestDto){
        Post post = postService.findPostById(requestDto.getPostId());
        Member sender = memberService.findMemberById(requestDto.getSenderId());
        Member receiver = memberService.findMemberById(requestDto.getReceiverId());
        MessageRoom messageRoom = messageRoomRepository.findByPostAndSenderAndReceiver(post,sender,receiver).orElse(null);
        if(messageRoom != null){
            return messageRoom.getMessageRoomId();
        }
        return null;
    }

    public MessageRoomListDto getMessageRoomList(Long memberId){
        List<MessageRoom> messageRoomList = findMessageRoomByMember(memberId);
        List<MessageRoomDto> messageRoomDtoList = messageService.findRecentMessageByMessageRoomId(messageRoomList);
        return new MessageRoomListDto(messageRoomDtoList);
    }

    @Transactional(readOnly = true)
    public List<MessageRoom> findMessageRoomByMember(Long memberId){
        Member member = memberService.findMemberById(memberId);
        List<MessageRoom> messageRoomList = messageRoomRepository.findBySenderOrReceiver(member);
        return messageRoomList;
    }

    @Transactional(readOnly = true)
    public MessageRoom findMessageRoomById(Long messageRoomId){
        MessageRoom messageRoom = messageRoomRepository.findById(messageRoomId).orElseThrow(()->{
            throw new IllegalArgumentException(messageRoomId + ": 존재하지 않는 쪽지방입니다.");
        });
        return messageRoom;
    }
}
