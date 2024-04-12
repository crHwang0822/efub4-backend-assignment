package efub.assignment.community.post.dto;

import lombok.*;
import java.util.List;

@Getter
@AllArgsConstructor
@Builder
public class PostListResponseDto {
    private List<PostDetailsResponseDto> posts;
    private Long count;
}
