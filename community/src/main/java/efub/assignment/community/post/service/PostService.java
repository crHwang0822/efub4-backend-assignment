package efub.assignment.community.post.service;

import efub.assignment.community.board.domain.Board;
import efub.assignment.community.board.service.BoardService;
import efub.assignment.community.member.domain.Member;
import efub.assignment.community.member.service.MemberService;
import efub.assignment.community.post.domain.Post;
import efub.assignment.community.post.dto.PostCreateRequestDto;
import efub.assignment.community.post.dto.PostResponseDto;
import efub.assignment.community.post.repository.PostRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final MemberService memberService;
    private final BoardService boardService;

    public PostResponseDto createPost(PostCreateRequestDto requestDto){

        Board board = boardService.findBoardById(requestDto.getBoardId());
        Member member = memberService.findMemberByNickname(requestDto.getWriterName());
        Post post = requestDto.toEntity(board,member);
        postRepository.save(post);
        PostResponseDto responseDto = PostResponseDto.toDto(post);
        return responseDto;
    }

}
