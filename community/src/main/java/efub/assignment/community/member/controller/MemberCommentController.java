package efub.assignment.community.member.controller;

import efub.assignment.community.comment.domain.Comment;
import efub.assignment.community.comment.service.CommentService;
import efub.assignment.community.member.dto.MemberCommentResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/members/{memberId}/comments")
@RequiredArgsConstructor
public class MemberCommentController {

    private final CommentService commentService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public MemberCommentResponseDto getCommentsByMemberId(@PathVariable final Long memberId){
        List<Comment> commentList = commentService.getCommentsByMemberId(memberId);
        MemberCommentResponseDto responseDto = MemberCommentResponseDto.toDto(memberId,commentList);
        return responseDto;
    }
}
