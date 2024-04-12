package efub.assignment.community.post.service;

import efub.assignment.community.board.domain.Board;
import efub.assignment.community.board.service.BoardService;
import efub.assignment.community.member.domain.Member;
import efub.assignment.community.member.service.MemberService;
import efub.assignment.community.post.domain.Post;
import efub.assignment.community.post.dto.PostCreateRequestDto;
import efub.assignment.community.post.dto.PostDetailsResponseDto;
import efub.assignment.community.post.dto.PostListResponseDto;
import efub.assignment.community.post.repository.PostRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final MemberService memberService;
    private final BoardService boardService;

    public PostDetailsResponseDto createPost(PostCreateRequestDto requestDto){

        Board board = boardService.findBoardById(requestDto.getBoardId());
        Member member = memberService.findMemberByNickname(requestDto.getWriterName());
        Post post = requestDto.toEntity(board,member);
        postRepository.save(post);
        PostDetailsResponseDto responseDto = PostDetailsResponseDto.toDto(post);
        return responseDto;
    }

    public PostDetailsResponseDto updatePost(Long postId, String content){
        Post post = findPostById(postId);
        post.updateContent(content);
        postRepository.save(post);
        PostDetailsResponseDto responseDto = PostDetailsResponseDto.toDto(post);
        return responseDto;
    }

    public Post findPostById(Long postId){
        Post post = postRepository.findById(postId).orElseThrow(()->{
            throw new EntityNotFoundException(postId + ": 존재하지 않는 게시글입니다.");
        });
        return post;
    }

    public PostListResponseDto findPostsByBoard(Long boardId){
        List<Post> posts = postRepository.findByBoard_BoardId(boardId);

        List<PostDetailsResponseDto> list = posts.stream()
                .map(post-> PostDetailsResponseDto.toDto(post))
                .collect(Collectors.toList());
        Long count = posts.stream().count();

        return PostListResponseDto.builder()
                .posts(list).count(count).build();
    }

}
