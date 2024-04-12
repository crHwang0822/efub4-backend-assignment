package efub.assignment.community.board.dto;

import efub.assignment.community.board.domain.Board;
import efub.assignment.community.member.domain.Member;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardRequestDto {
    @NotBlank
    private String ownerName;

    @NotBlank
    @Size(min=2,max=20)
    private String name;

    @NotBlank
    @Size(max=100)
    private String description;

    private String notice;

    public Board toEntity(Member member){
        return Board.builder()
                .member(member)
                .name(this.name)
                .description(this.description)
                .notice(this.notice)
                .build();
    }
}
