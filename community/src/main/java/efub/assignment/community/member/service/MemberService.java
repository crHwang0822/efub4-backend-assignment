package efub.assignment.community.member.service;

import efub.assignment.community.member.domain.Member;
import efub.assignment.community.member.dto.MemberResponseDto;
import efub.assignment.community.member.dto.MemberRequestDto;
import efub.assignment.community.member.repository.MemberRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberResponseDto signUp(MemberRequestDto requestDto){

        if(memberRepository.existsByEmail(requestDto.getEmail())){
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }
        if(memberRepository.existsByNickname(requestDto.getNickname())){
            throw new IllegalArgumentException("이미 존재하는 닉네임입니다.");
        }

        Member member = memberRepository.save(requestDto.toEntity());
        MemberResponseDto responseDto = MemberResponseDto.toDto(member);
        return responseDto;
    }

    public MemberResponseDto getMember(Long memberId){
        Member member = findMemberById(memberId);
        MemberResponseDto responseDto = MemberResponseDto.toDto(member);
        return responseDto;
    }

    public MemberResponseDto updateMember(Long memberId, MemberRequestDto requestDto){
        if(memberRepository.existsByNickname(requestDto.getNickname())){
            throw new IllegalArgumentException("이미 존재하는 닉네임입니다.");
        }
        Member member = findMemberById(memberId);
        member.changeNickname(requestDto.getNickname());
        MemberResponseDto responseDto = MemberResponseDto.toDto(memberRepository.save(member));
        return responseDto;
    }

    public void withdraw(Long memberId){
        Member member = findMemberById(memberId);
        member.deactivateAccount();
        memberRepository.save(member);
    }

    public Member findMemberByNickname(String nickname){
        Member member = memberRepository.findByNickname(nickname).orElseThrow(()-> {
                    throw new EntityNotFoundException(nickname + ": 존재하지 않는 회원입니다.");
                });
        return member;
    }

    public Member findMemberById(Long memberId){
        Member member = memberRepository.findById(memberId).orElseThrow(()->{
            throw new EntityNotFoundException(memberId + ": 존재하지 않는 회원입니다.");
        });
        return member;
    }

}
