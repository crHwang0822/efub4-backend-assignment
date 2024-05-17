package efub.assignment.community.post.controller;

import efub.assignment.community.post.dto.PostHeartRequestDto;
import efub.assignment.community.post.service.PostHeartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/postHearts")
public class PostHeartController {
    
    private final PostHeartService postHeartService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String createPostHeart(@RequestBody @Valid PostHeartRequestDto requestDto){
        postHeartService.create(requestDto);
        return "해당 게시글에 대한 좋아요가 추가되었습니다.";
    }
}
