package dnaaaaahtac.wooriforei.domain.board.service;


import dnaaaaahtac.wooriforei.domain.board.dto.BoardRequestDTO;
import dnaaaaahtac.wooriforei.domain.board.dto.BoardResponseDTO;
import dnaaaaahtac.wooriforei.domain.board.entity.Board;
import dnaaaaahtac.wooriforei.domain.board.repository.BoardRepository;
import dnaaaaahtac.wooriforei.domain.user.entity.User;
import dnaaaaahtac.wooriforei.domain.user.repository.UserRepository;
import dnaaaaahtac.wooriforei.global.exception.CustomException;
import dnaaaaahtac.wooriforei.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoradService {

    private final BoardRepository boardRepository;

    @Transactional
    public BoardResponseDTO createBoard(User user, BoardRequestDTO boardRequestDTO){

        if(boardRequestDTO.getTitle().length()>225){
            throw new CustomException(ErrorCode.TITLE_TOO_LONG);
        }

        if(boardRequestDTO.getContent().length()>1000){
            throw new CustomException(ErrorCode.CONTENT_TOO_LONG);
        }

        if(boardRequestDTO.getTitle().isEmpty()){
            throw new CustomException(ErrorCode.TITLE_REQUIRED);
        }

        if(boardRequestDTO.getContent().isEmpty()){
            throw new CustomException(ErrorCode.CONTENT_REQUIRED);
        }

        Board board = new Board();
        board.setUser(user);
        board.setTitle(boardRequestDTO.getTitle());
        board.setContent(boardRequestDTO.getContent());
        board.setImage(boardRequestDTO.getImage());
        board.setCreatedAt(LocalDateTime.now());
        board.setModifiedAt(LocalDateTime.now());
        boardRepository.save(board);

        return new BoardResponseDTO(board);
    }

    @Transactional(readOnly = true)
    public List<BoardResponseDTO> getAllBoards(){

        return boardRepository.findAll().stream()
                .map(BoardResponseDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public BoardResponseDTO getBoardById(Long boardId){

         Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_BOARD));

        return new BoardResponseDTO(board);
    }

    @Transactional
    public BoardResponseDTO updateBoard(User user,Long boardId, BoardRequestDTO boardRequestDTO){

        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_BOARD));

        if (!board.getUser().getUserId().equals(user.getUserId())) {
            throw new CustomException(ErrorCode.FORBIDDEN_WORK);
        }

        if(boardRequestDTO.getTitle().length()>225){
            throw new CustomException(ErrorCode.TITLE_TOO_LONG);
        }

        if(boardRequestDTO.getContent().length()>1000){
            throw new CustomException(ErrorCode.CONTENT_TOO_LONG);
        }

        if(boardRequestDTO.getTitle().isEmpty()){
            throw new CustomException(ErrorCode.TITLE_REQUIRED);
        }

        if(boardRequestDTO.getContent().isEmpty()){
            throw new CustomException(ErrorCode.CONTENT_REQUIRED);
        }

        board.setTitle(boardRequestDTO.getTitle());
        board.setContent(boardRequestDTO.getContent());
        board.setImage(boardRequestDTO.getImage());
        board.setModifiedAt(LocalDateTime.now());
        boardRepository.save(board);

        return new BoardResponseDTO(board);
    }


    @Transactional
    public void deleteBoard(User user, Long boardId){

        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_BOARD));

        if (!board.getUser().getUserId().equals(user.getUserId()) && !board.getUser().getIsAdmin()) {
            throw new CustomException(ErrorCode.FORBIDDEN_WORK);
        }

        boardRepository.delete(board);
    }

}
