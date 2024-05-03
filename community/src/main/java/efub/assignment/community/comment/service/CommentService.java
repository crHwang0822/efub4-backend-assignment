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
import efub.assignment.community.post.dto.PostCommentResponseDto;
import efub.assignment.community.post.service.PostService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.util.List;
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

    public CommentDetailsResponseDto updatePost(Long commentId, CommentUpdateRequestDto requestDto){
        Comment comment = findCommentById(commentId);

        //글 작성자와 요청한 회원 비교
        Member writer = comment.getMember();
        Member requester = memberService.findMemberByNickname(requestDto.getWriterNickname());
        if(!(writer.equals(requester))){
            throw new IllegalArgumentException("해당 댓글을 수정할 권한이 없습니다.");
        }

        //글 수정
        comment.updateContent(requestDto.getContent());
        commentRepository.save(comment);
        commentRepository.flush();

        CommentDetailsResponseDto responseDto = CommentDetailsResponseDto.toDto(comment);
        return responseDto;
    }

    public Comment findCommentById(Long commentId){
        Comment comment = commentRepository.findById(commentId).orElseThrow(()->{
            throw new EntityNotFoundException(commentId + ": 존재하지 않는 댓글입니다.");
        });

        return comment;
    }

    public void deleteComment(Long commentId, CommentDeleteRequestDto requestDto){
        Comment comment = findCommentById(commentId);

        //글 작성자와 삭제 요청한 회원 비교
        Member writer = comment.getMember();
        Member requester = memberService.findMemberByNickname(requestDto.getWriterNickname());
        if(!(writer.equals(requester))){
            throw new IllegalArgumentException("해당 댓글을 삭제할 권한이 없습니다.");
        }

        //글 삭제
        commentRepository.delete(comment);
    }

    public List<Comment> getCommentsByPostId(Long postId){
        Post post = postService.findPostById(postId);
        List<Comment> commentList = post.getCommentList();
        return commentList;
    }

}
