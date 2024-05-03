package efub.assignment.community.member.controller;

import efub.assignment.community.member.dto.MemberResponseDto;
import efub.assignment.community.member.dto.MemberRequestDto;
import efub.assignment.community.member.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public MemberResponseDto signUp(@RequestBody @Valid final MemberRequestDto requestDto){
        MemberResponseDto responseDto = memberService.signUp(requestDto);
        return responseDto;
    }

    @GetMapping("/{memberId}")
    @ResponseStatus(value = HttpStatus.OK)
    public MemberResponseDto getMember(@PathVariable Long memberId){
        MemberResponseDto responseDto = memberService.getMember(memberId);
        return responseDto;
    }

    @PatchMapping("/profile/{memberId}")
    @ResponseStatus(value = HttpStatus.OK)
    public MemberResponseDto updateMember(@PathVariable Long memberId, @RequestBody @Valid MemberRequestDto requestDto){
        MemberResponseDto responseDto = memberService.updateMember(memberId, requestDto);
        return responseDto;
    }

    @PatchMapping("{memberId}")
    @ResponseStatus(value = HttpStatus.OK)
    public String withdraw(@PathVariable Long memberId){
        memberService.withdraw(memberId);
        return "삭제가 완료되었습니다.";
    }


}
