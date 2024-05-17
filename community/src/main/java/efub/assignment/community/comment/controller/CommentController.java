package efub.assignment.community.comment.controller;

import efub.assignment.community.comment.dto.CommentCreateRequestDto;
import efub.assignment.community.comment.dto.CommentDeleteRequestDto;
import efub.assignment.community.comment.dto.CommentDetailsResponseDto;
import efub.assignment.community.comment.dto.CommentUpdateRequestDto;
import efub.assignment.community.comment.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CommentDetailsResponseDto createComment(@RequestBody @Valid CommentCreateRequestDto requestDto){
        CommentDetailsResponseDto response = commentService.createComment(requestDto);
        return response;
    }

    @PatchMapping("/{commentId}")
    @ResponseStatus(HttpStatus.OK)
    public CommentDetailsResponseDto updateComment(@PathVariable final Long commentId, @RequestBody @Valid CommentUpdateRequestDto requestDto){
        CommentDetailsResponseDto response = commentService.updatePost(commentId,requestDto);
        return response;
    }

    @DeleteMapping("/{commentId}")
    @ResponseStatus(HttpStatus.OK)
    public String deleteComment(@PathVariable final Long commentId, @RequestBody@ Valid CommentDeleteRequestDto requestDto){
        commentService.deleteComment(commentId,requestDto);
        return "댓글을 삭제했습니다.";
    }
}
