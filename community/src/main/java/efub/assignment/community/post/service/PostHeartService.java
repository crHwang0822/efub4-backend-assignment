package efub.assignment.community.post.service;

import efub.assignment.community.member.domain.Member;
import efub.assignment.community.member.service.MemberService;
import efub.assignment.community.post.domain.Post;
import efub.assignment.community.post.domain.PostHeart;
import efub.assignment.community.post.dto.PostHeartRequestDto;
import efub.assignment.community.post.repository.PostHeartRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class PostHeartService {

    private final PostHeartRepository postHeartRepository;

    private final MemberService memberService;
    private final PostService postService;

    public void createPostHeart(PostHeartRequestDto requestDto){
        Member member = memberService.findMemberById(requestDto.getMemberId());
        Post post = postService.findPostById(requestDto.getPostId());

        if(postHeartRepository.existsByPostAndMember(post,member)){
            throw new IllegalArgumentException("이미 좋아요를 누른 게시글입니다.");
        }

        PostHeart postHeart = PostHeart.builder()
                .post(post)
                .member(member)
                .build();

        postHeartRepository.save(postHeart);

    }

    public void deletePostHeart(PostHeartRequestDto requestDto){
        Member member = memberService.findMemberById(requestDto.getMemberId());
        Post post = postService.findPostById(requestDto.getPostId());

        PostHeart postHeart = postHeartRepository.findByPostAndMember(post,member).orElseThrow(()->{
            throw new IllegalArgumentException("좋아요가 존재하지 않습니다.");
        });

        postHeartRepository.delete(postHeart);
    }
}
