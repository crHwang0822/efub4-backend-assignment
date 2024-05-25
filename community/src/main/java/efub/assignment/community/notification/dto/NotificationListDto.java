package efub.assignment.community.notification.dto;

import lombok.*;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
@AllArgsConstructor
public class NotificationListDto {
    private List<NotificationDto> list;
}
