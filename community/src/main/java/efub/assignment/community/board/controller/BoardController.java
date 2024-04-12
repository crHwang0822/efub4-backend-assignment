package efub.assignment.community.board.controller;

import efub.assignment.community.board.dto.BoardRequestDto;
import efub.assignment.community.board.dto.BoardResponseDto;
import efub.assignment.community.board.dto.BoardUpdateRequestDto;
import efub.assignment.community.board.service.BoardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/boards")
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BoardResponseDto createBoard(@RequestBody @Valid BoardRequestDto requestDto){
        BoardResponseDto responseDto = boardService.createBoard(requestDto);
        return responseDto;
    }

    @PatchMapping("/{boardId}")
    @ResponseStatus(HttpStatus.OK)
    public BoardResponseDto updateBoard(@PathVariable Long boardId, @RequestBody @Valid BoardUpdateRequestDto requestDto){
        BoardResponseDto responseDto = boardService.updateBoard(boardId, requestDto.getOwnerName());
        return responseDto;
    }

}
