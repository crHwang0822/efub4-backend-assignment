package efub.assignment.community.board.dto;

import efub.assignment.community.board.domain.Board;
import lombok.*;

import java.time.LocalDateTime;

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
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    public static BoardResponseDto toDto(Board board){
        return BoardResponseDto.builder()
                .boardId(board.getBoardId())
                .ownerName(board.getMember().getNickname())
                .name(board.getName())
                .description(board.getDescription())
                .notice(board.getNotice())
                .createdDate(board.getRegDate())
                .modifiedDate(board.getModDate())
                .build();
    }

}
