package efub.assignment.community.notification.service;

import efub.assignment.community.member.domain.Member;
import efub.assignment.community.notification.domain.Notification;
import efub.assignment.community.notification.domain.NotificationType;
import efub.assignment.community.notification.repository.NotificationRepository;
import efub.assignment.community.post.domain.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final NotificationRepository notificationRepository;

    //게시글에 댓글이 달리면 게시글 작성자에게 알림 생성
    public void createCommentNotification(Member commentWriter, String content, Post post){
        if(commentWriter.equals(post.getMember())){
            return;
        }

        Notification noti = Notification.builder()
                .member(post.getMember())
                .type(NotificationType.COMMENT)
                .content(content)
                .post(post)
                .build();
        notificationRepository.save(noti);
    }

    //쪽지방이 생기면 쪽지를 받은 사람에게 알림 생성
    public void createMessageRoomNotification(Member member, String content){
        Notification noti = Notification.builder()
                .member(member)
                .type(NotificationType.MESSAGE_ROOM)
                .content(content)
                .build();
        notificationRepository.save(noti);
    }
}
