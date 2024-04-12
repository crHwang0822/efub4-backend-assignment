package efub.assignment.community.board.dto;

import efub.assignment.community.board.domain.Board;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardResponseDto {

    private Long boardId;
    private String ownerName;
    private String name;
    private String description;
    private String notice;

    public static BoardResponseDto toDto(Board board){
        return BoardResponseDto.builder()
                .boardId(board.getBoardId())
                .ownerName(board.getMember().getNickname())
                .name(board.getName())
                .description(board.getDescription())
                .notice(board.getNotice())
                .build();
    }

}
