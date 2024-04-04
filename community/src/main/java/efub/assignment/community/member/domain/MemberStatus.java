package efub.assignment.community.member.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MemberStatus {

    REGISTERED(0,"등록 상태"),
    UNREGISTERED(1,"탈퇴 상태");

    private final Integer id;
    private final String title;
}
