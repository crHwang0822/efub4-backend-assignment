package efub.assignment.community.post.controller;


import efub.assignment.community.comment.domain.Comment;
import efub.assignment.community.comment.service.CommentService;
import efub.assignment.community.post.dto.PostCommentResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts/{postId}/comments")
@RequiredArgsConstructor
public class PostCommentController {

    private final CommentService commentService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public PostCommentResponseDto getCommentsByPostId(@PathVariable final Long postId){
        List<Comment> commentList = commentService.getCommentsByPostId(postId);
        PostCommentResponseDto responseDto = PostCommentResponseDto.toDto(postId,commentList);
        return responseDto;
    }
}
