package efub.assignment.community.post.dto;

import efub.assignment.community.post.domain.Post;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostResponseDto {
    private Long postId;
    private Long boardId;
    private String writerName;
    private boolean isAnonymous;
    private String content;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    public static PostResponseDto toDto(Post post){
        return PostResponseDto.builder()
                .postId(post.getPostId())
                .boardId(post.getBoard().getBoardId())
                .writerName(post.getMember().getNickname())
                .isAnonymous(post.isAnonymous())
                .content(post.getContent())
                .createdDate(post.getRegDate())
                .modifiedDate(post.getModDate())
                .build();
    }
}
