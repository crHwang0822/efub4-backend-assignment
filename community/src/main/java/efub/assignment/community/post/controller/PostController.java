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
    public PostDetailsResponseDto updatePost(@PathVariable Long postId, @RequestBody @Valid PostUpdateRequestDto requestDto){
        PostDetailsResponseDto responseDto = postService.updatePost(postId,requestDto.getContent());
        return responseDto;
    }

    //특정 게시판의 게시글 목록 조회
    @GetMapping("/list/{boardId}")
    @ResponseStatus(HttpStatus.OK)
    public PostListResponseDto getAllPosts(@PathVariable Long boardId){
        PostListResponseDto responseDto = postService.findPostsByBoard(boardId);
        return responseDto;
    }
}
