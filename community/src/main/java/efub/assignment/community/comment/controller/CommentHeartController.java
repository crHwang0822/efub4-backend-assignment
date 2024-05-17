package efub.assignment.community.comment.controller;

import efub.assignment.community.comment.dto.CommentHeartRequestDto;
import efub.assignment.community.comment.service.CommentHeartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/commentHearts")
public class CommentHeartController {

    private final CommentHeartService commentHeartService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String createCommentHeart(@RequestBody @Valid CommentHeartRequestDto requestDto){
        commentHeartService.createCommentHeaert(requestDto);
        return "해당 댓글에 대한 좋아요가 추가되었습니다.";
    }
}
