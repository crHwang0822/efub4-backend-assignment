package efub.assignment.community.member.controller;

import efub.assignment.community.member.dto.MemberResponseDto;
import efub.assignment.community.member.dto.SignUpRequestDto;
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
    public MemberResponseDto signUp(@RequestBody @Valid final SignUpRequestDto requestDto){
        MemberResponseDto responseDto = memberService.signUp(requestDto);
        return responseDto;
    }

}
