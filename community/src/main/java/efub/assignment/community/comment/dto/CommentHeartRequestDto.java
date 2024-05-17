package efub.assignment.community.comment.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class CommentHeartRequestDto {
    @NotNull
    private Long commentId;
    @NotNull
    private Long memberId;
}
