package efub.assignment.community.board.service;

import efub.assignment.community.board.domain.Board;
import efub.assignment.community.board.dto.BoardRequestDto;
import efub.assignment.community.board.dto.BoardResponseDto;
import efub.assignment.community.board.repository.BoardRepository;
import efub.assignment.community.member.domain.Member;
import efub.assignment.community.member.service.MemberService;
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
    private final MemberService memberService;

    public BoardResponseDto createBoard(BoardRequestDto requestDto){
        if(boardRepository.existsByName(requestDto.getName())){
            throw new EntityExistsException(requestDto.getName() + ": 이미 존재하는 게시판 이름입니다.");
        }
        Member member = memberService.findMemberByNickname(requestDto.getOwnerName());
        Board board = requestDto.toEntity(member);
        boardRepository.save(board);
        BoardResponseDto responseDto = BoardResponseDto.toDto(board);
        return responseDto;
    }

    public BoardResponseDto updateBoard(Long boardId, String ownerName){
        Board board = boardRepository.findById(boardId)
                .orElseThrow(()-> {throw new EntityNotFoundException(boardId + ": 존재하지 않는 게시판입니다.");});
        Member member = memberService.findMemberByNickname(ownerName);

        board.changeOwner(member);
        boardRepository.save(board);
        boardRepository.flush();
        BoardResponseDto responseDto = BoardResponseDto.toDto(board);
        return responseDto;
    }

    public BoardResponseDto getBoard(Long boardId){
        Board board = boardRepository.findById(boardId).orElseThrow(()->{
            throw new EntityNotFoundException(boardId + ": 존재하지 않는 게시판입니다.");
        });
        BoardResponseDto responseDto = BoardResponseDto.toDto(board);
        return responseDto;
    }

    public void deleteBoard(Long boardId){
        Board board = boardRepository.findById(boardId).orElseThrow(()->{
            throw new EntityNotFoundException(boardId + ": 존재하지 않는 게시판입니다.");
        });

        boardRepository.deleteById(boardId);
    }
}
