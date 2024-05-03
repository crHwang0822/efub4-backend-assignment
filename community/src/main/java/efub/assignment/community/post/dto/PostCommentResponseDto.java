package efub.assignment.community.post.dto;

import efub.assignment.community.comment.domain.Comment;
import efub.assignment.community.comment.dto.CommentDetailsResponseDto;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostCommentResponseDto {
    private Long postId;
    private Long count;
    private List<CommentDetailsResponseDto> commentList;

    public static PostCommentResponseDto toDto(Long postId,List<Comment> commentList){
        return PostCommentResponseDto.builder()
                .postId(postId)
                .count((long) commentList.size())
                .commentList(commentList.stream().map(comment -> CommentDetailsResponseDto.toDto(comment)).collect(Collectors.toList()))
                .build();
    }
}
