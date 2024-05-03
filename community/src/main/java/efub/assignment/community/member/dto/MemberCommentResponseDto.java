package efub.assignment.community.member.dto;

import efub.assignment.community.comment.domain.Comment;
import efub.assignment.community.comment.dto.CommentDetailsResponseDto;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberCommentResponseDto {
    private Long memberId;
    private Long count;
    private List<CommentDetailsResponseDto> commentList;

    public static MemberCommentResponseDto toDto(Long memberId, List<Comment> commentList){
        return MemberCommentResponseDto.builder()
                .memberId(memberId)
                .count((long)commentList.size())
                .commentList(commentList.stream().map(comment -> CommentDetailsResponseDto.toDto(comment)).collect(Collectors.toList()))
                .build();
    }
}
