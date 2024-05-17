package efub.assignment.community.post.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class PostHeartRequestDto {
    @NotNull
    private Long postId;
    @NotNull
    private Long memberId;
}
