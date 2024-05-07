package dnaaaaahtac.wooriforei.domain.comment.service;

import dnaaaaahtac.wooriforei.domain.board.entity.Board;
import dnaaaaahtac.wooriforei.domain.board.repository.BoardRepository;
import dnaaaaahtac.wooriforei.domain.comment.dto.CommentRequestDTO;
import dnaaaaahtac.wooriforei.domain.comment.dto.CommentResponseDTO;
import dnaaaaahtac.wooriforei.domain.comment.entity.Comment;
import dnaaaaahtac.wooriforei.domain.comment.repository.CommentRepository;
import dnaaaaahtac.wooriforei.domain.user.entity.User;
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
public class CommentService {

    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public CommentResponseDTO createComment(Long boardId, User user, CommentRequestDTO commentRequestDTO) {

        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_BOARD));

        if (commentRequestDTO.getCommentContent().length() > 500) {
            throw new CustomException(ErrorCode.COMMENT_TOO_LONG);
        }

        if (commentRequestDTO.getCommentContent().isEmpty()) {
            throw new CustomException(ErrorCode.CONTENT_REQUIRED);
        }

        Comment comment = new Comment();
        comment.setUser(user);
        comment.setBoard(board);
        comment.setContent(commentRequestDTO.getCommentContent());
        comment.setCreateAt(LocalDateTime.now());
        comment.setModifiedAt(LocalDateTime.now());

        commentRepository.save(comment);

        return new CommentResponseDTO(comment);
    }

    @Transactional
    public CommentResponseDTO updateComment(Long commentId, User user, CommentRequestDTO commentRequestDTO) {

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_COMMENT));

        if (!user.getId().equals(comment.getUser().getId())) {
            throw new CustomException(ErrorCode.FORBIDDEN_WORK);
        }

        if (commentRequestDTO.getCommentContent().length() > 500) {
            throw new CustomException(ErrorCode.COMMENT_TOO_LONG);
        }

        if (commentRequestDTO.getCommentContent().isEmpty()) {
            throw new CustomException(ErrorCode.CONTENT_REQUIRED);
        }

        comment.setContent(commentRequestDTO.getCommentContent());
        comment.setModifiedAt(LocalDateTime.now());

        commentRepository.save(comment);

        return new CommentResponseDTO(comment);
    }

    @Transactional
    public void deleteComment(Long commentId, User user) {

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_COMMENT));

        if (!user.getId().equals(comment.getUser().getId())) {
            throw new CustomException(ErrorCode.FORBIDDEN_WORK);
        }

        commentRepository.delete(comment);
    }

    @Transactional
    public List<CommentResponseDTO> checkMyComments(Long userId) {

        return commentRepository.findByUser_UserId(userId).stream()
                .map(CommentResponseDTO::new)
                .collect(Collectors.toList());
    }

}
