package efub.assignment.community.comment.service;

import efub.assignment.community.comment.domain.Comment;
import efub.assignment.community.comment.dto.CommentCreateRequestDto;
import efub.assignment.community.comment.dto.CommentDeleteRequestDto;
import efub.assignment.community.comment.dto.CommentDetailsResponseDto;
import efub.assignment.community.comment.dto.CommentUpdateRequestDto;
import efub.assignment.community.comment.repository.CommentRepository;
import efub.assignment.community.member.domain.Member;
import efub.assignment.community.member.service.MemberService;
import efub.assignment.community.post.domain.Post;
import efub.assignment.community.post.service.PostService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.util.NoSuchElementException;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final MemberService memberService;
    private final PostService postService;

    public CommentDetailsResponseDto createComment(CommentCreateRequestDto requestDto){
        Post post = postService.findPostById(requestDto.getPostId());
        Member member = memberService.findMemberByNickname(requestDto.getWriterNickname());
        Comment comment = requestDto.toEntity(post,member);
        commentRepository.save(comment);
        CommentDetailsResponseDto responseDto = CommentDetailsResponseDto.toDto(comment);
        return responseDto;
    }
}
