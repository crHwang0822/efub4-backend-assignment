package efub.assignment.community.notification.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum NotificationType {

    COMMENT(1,"댓글 알림"),
    MESSAGE_ROOM(2, "쪽지방 알림");

    private final Integer id;
    private final String type;
}
