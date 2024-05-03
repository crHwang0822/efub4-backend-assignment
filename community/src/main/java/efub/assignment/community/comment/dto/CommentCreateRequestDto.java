package efub.assignment.community.comment.dto;

import efub.assignment.community.comment.domain.Comment;
import efub.assignment.community.member.domain.Member;
import efub.assignment.community.post.domain.Post;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentCreateRequestDto {

    @NotNull
    private Long postId;

    @NotBlank
    private String writerNickname;

    @NotBlank
    private String content;

    public Comment toEntity(Post post, Member member){
        return Comment.builder()
                .post(post)
                .member(member)
                .content(this.content)
                .build();
    }
}
