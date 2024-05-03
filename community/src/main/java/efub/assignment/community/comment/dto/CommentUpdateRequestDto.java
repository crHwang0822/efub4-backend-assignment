package efub.assignment.community.comment.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentUpdateRequestDto {
    @NotBlank
    private String content;
    @NotBlank
    private String writerNickname;
}
