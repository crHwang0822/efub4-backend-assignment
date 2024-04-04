package efub.assignment.community.member.dto;

import efub.assignment.community.member.domain.Member;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GetMemberResponseDto {
    private Long memberId;
    private String email;
    private String nickname;
    private String university;
    private String studentId;

    public static GetMemberResponseDto toDto(Member member){
        return GetMemberResponseDto.builder()
                .memberId(member.getMemberId())
                .email(member.getEmail())
                .nickname(member.getNickname())
                .university(member.getUniversity())
                .studentId(member.getStudentId())
                .build();
    }
}
