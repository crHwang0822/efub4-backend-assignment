package efub.assignment.community.messageRoom.service;

import efub.assignment.community.member.domain.Member;
import efub.assignment.community.member.service.MemberService;
import efub.assignment.community.messageRoom.domain.MessageRoom;
import efub.assignment.community.messageRoom.dto.MessageRoomCreateRequestDto;
import efub.assignment.community.messageRoom.dto.MessageRoomCreateResponseDto;
import efub.assignment.community.messageRoom.dto.MessageRoomExistsRequestDto;
import efub.assignment.community.messageRoom.repository.MessageRoomRepository;
import efub.assignment.community.post.domain.Post;
import efub.assignment.community.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class MessageRoomService {

    private final MessageRoomRepository messageRoomRepository;
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


}
