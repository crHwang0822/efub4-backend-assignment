package efub.assignment.community.comment.dto;

import efub.assignment.community.comment.domain.Comment;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentDetailsResponseDto {
    private Long comment_id;
    private Long post_id;
    private String writerNickname;
    private String content;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    public static CommentDetailsResponseDto toDto(Comment comment){
        return CommentDetailsResponseDto.builder()
                .comment_id(comment.getCommentId())
                .post_id(comment.getPost().getPostId())
                .writerNickname(comment.getMember().getNickname())
                .content(comment.getContent())
                .createdDate(comment.getRegDate())
                .modifiedDate(comment.getModDate())
                .build();
    }
}
