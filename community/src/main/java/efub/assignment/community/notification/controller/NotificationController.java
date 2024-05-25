package efub.assignment.community.notification.controller;

import efub.assignment.community.member.domain.Member;
import efub.assignment.community.member.service.MemberService;
import efub.assignment.community.notification.dto.NotificationListDto;
import efub.assignment.community.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/notifications")
public class NotificationController {

    private final NotificationService notificationService;
    private final MemberService memberService;

    @GetMapping("/list/{memberId}")
    @ResponseStatus(HttpStatus.OK)
    public NotificationListDto getNotificationList(@PathVariable("memberId") final Long memberId){
        Member member = memberService.findMemberById(memberId);
        return notificationService.getNotificationList(member);
    }
}
