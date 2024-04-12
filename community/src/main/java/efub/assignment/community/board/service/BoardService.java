package efub.assignment.community.board.service;

import efub.assignment.community.board.domain.Board;
import efub.assignment.community.board.dto.BoardRequestDto;
import efub.assignment.community.board.dto.BoardResponseDto;
import efub.assignment.community.board.repository.BoardRepository;
import efub.assignment.community.member.domain.Member;
import efub.assignment.community.member.repository.MemberRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final  MemberRepository memberRepository;

    public BoardResponseDto createBoard(BoardRequestDto requestDto){
        if(boardRepository.existsByName(requestDto.getName())){
            throw new EntityExistsException(requestDto.getName() + ": 이미 존재하는 게시판 이름입니다.");
        }
        if(!memberRepository.existsByNickname(requestDto.getOwnerName())){
            throw new EntityNotFoundException(requestDto.getOwnerName() + ": 존재하지 않는 회원입니다.");
        }
        Member member = memberRepository.findByNickname(requestDto.getOwnerName());
        Board board = requestDto.toEntity(member);
        boardRepository.save(board);
        BoardResponseDto responseDto = BoardResponseDto.toDto(board);
        return responseDto;
    }
}
