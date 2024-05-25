package efub.assignment.community.message.service;

import efub.assignment.community.message.domain.Message;
import efub.assignment.community.message.repository.MessageRepository;
import efub.assignment.community.messageRoom.domain.MessageRoom;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final MessageRepository messageRepository;

    public void createFirstMessage(final MessageRoom messageRoom){
        Message message = Message.builder()
                .messageRoom(messageRoom)
                .sender(messageRoom.getSender())
                .content(messageRoom.getContent())
                .build();
        messageRepository.save(message);
    }


}
