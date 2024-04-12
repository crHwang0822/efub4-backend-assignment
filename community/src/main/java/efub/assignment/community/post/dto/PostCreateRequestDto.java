package efub.assignment.community.post.dto;

import efub.assignment.community.board.domain.Board;
import efub.assignment.community.member.domain.Member;
import efub.assignment.community.post.domain.Post;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostCreateRequestDto {

    @NotNull
    private Long boardId;

    @NotBlank
    private String writerName;

    @NotNull
    private boolean isAnonymous;

    @NotBlank
    private String content;

    public Post toEntity(Board board, Member member){
        return Post.builder()
                .board(board)
                .member(member)
                .isAnonymous(this.isAnonymous)
                .content(this.content)
                .build();
    }
}
