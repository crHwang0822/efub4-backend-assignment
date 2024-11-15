package efub.assignment.community.post.controller;


import efub.assignment.community.post.dto.PostCreateRequestDto;
import efub.assignment.community.post.dto.PostDetailsResponseDto;
import efub.assignment.community.post.dto.PostListResponseDto;
import efub.assignment.community.post.dto.PostUpdateRequestDto;
import efub.assignment.community.post.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PostDetailsResponseDto createPost(@RequestBody @Valid final PostCreateRequestDto requestDto){
        PostDetailsResponseDto responseDto = postService.createPost(requestDto);
        return responseDto;
    }

    @PatchMapping("/{postId}")
    @ResponseStatus(HttpStatus.OK)
    public PostDetailsResponseDto updatePost(@PathVariable final Long postId, @RequestBody @Valid PostUpdateRequestDto requestDto){
        PostDetailsResponseDto responseDto = postService.updatePost(postId,requestDto.getContent());
        return responseDto;
    }

    //특정 게시판의 게시글 목록 조회
    @GetMapping("/list/{boardId}")
    @ResponseStatus(HttpStatus.OK)
    public PostListResponseDto getAllPosts(@PathVariable final Long boardId){
        PostListResponseDto responseDto = postService.findPostsByBoard(boardId);
        return responseDto;
    }

    //게시글 1개 상세 조회
    @GetMapping("/{postId}")
    @ResponseStatus(HttpStatus.OK)
    public PostDetailsResponseDto getPost(@PathVariable final Long postId){
        PostDetailsResponseDto responseDto = postService.getPost(postId);
        return responseDto;
    }

    @DeleteMapping("/{postId}")
    @ResponseStatus(HttpStatus.OK)
    public String deletePost(@PathVariable Long postId){
        postService.deletePost(postId);
        return "게시글을 삭제했습니다.";
    }

    @GetMapping("/search")
    @ResponseStatus(value = HttpStatus.OK)
    public PostListResponseDto searchPost(
        @RequestParam(value = "boardId", required = false) final Long boardId,
        @RequestParam(value = "writer", required = false) final String writer,
        @RequestParam(value = "content", required = false) final String content){
        return postService.searchPost(boardId, writer, content);
    }
}
