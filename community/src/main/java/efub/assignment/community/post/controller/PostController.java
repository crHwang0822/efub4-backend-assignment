package efub.assignment.community.post.controller;


import efub.assignment.community.post.dto.PostCreateRequestDto;
import efub.assignment.community.post.dto.PostResponseDto;
import efub.assignment.community.post.dto.PostUpdateRequestDto;
import efub.assignment.community.post.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PostResponseDto createPost(@RequestBody @Valid final PostCreateRequestDto requestDto){
        PostResponseDto responseDto = postService.createPost(requestDto);
        return responseDto;
    }

    @PatchMapping("/{postId}")
    @ResponseStatus(HttpStatus.OK)
    public PostResponseDto updatePost(@PathVariable Long postId, @RequestBody @Valid PostUpdateRequestDto requestDto){
        PostResponseDto responseDto = postService.updatePost(postId,requestDto.getContent());
        return responseDto;
    }
}
