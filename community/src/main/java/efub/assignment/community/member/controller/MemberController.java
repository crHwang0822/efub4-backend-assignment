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
    @ResponseStatus(value = HttpStatus.OK)
    public MemberResponseDto signUp(@RequestBody @Valid final MemberRequestDto requestDto){
        MemberResponseDto responseDto = memberService.signUp(requestDto);
        return responseDto;
    }

    @PatchMapping("/profile/{memberId}")
    @ResponseStatus(value = HttpStatus.OK)
    public MemberResponseDto updateMember(@PathVariable Long memberId, @RequestBody @Valid MemberRequestDto requestDto){
        MemberResponseDto responseDto = memberService.updateMember(memberId, requestDto);
        return responseDto;
    }


}
