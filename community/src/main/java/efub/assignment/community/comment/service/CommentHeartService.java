package efub.assignment.community.comment.service;

import efub.assignment.community.comment.domain.Comment;
import efub.assignment.community.comment.domain.CommentHeart;
import efub.assignment.community.comment.dto.CommentHeartRequestDto;
import efub.assignment.community.comment.repository.CommentHeartRepository;
import efub.assignment.community.member.domain.Member;
import efub.assignment.community.member.service.MemberService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentHeartService {

    private final CommentHeartRepository commentHeartRepository;

    private final CommentService commentService;
    private final MemberService memberService;

    public void createCommentHeaert(CommentHeartRequestDto requestDto){
        Member member = memberService.findMemberById(requestDto.getMemberId());
        Comment comment = commentService.findCommentById(requestDto.getCommentId());

        if(commentHeartRepository.existsByCommentAndMember(comment,member)){
            throw new IllegalArgumentException("이미 좋아요를 누른 댓글입니다.");
        }

        CommentHeart commentHeart = CommentHeart.builder()
                .comment(comment)
                .member(member)
                .build();

        commentHeartRepository.save(commentHeart);
    }
}
