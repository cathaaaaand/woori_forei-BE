package dnaaaaahtac.wooriforei.domain.board.dto;

import dnaaaaahtac.wooriforei.domain.board.entity.Board;
import dnaaaaahtac.wooriforei.domain.board.entity.BoardImage;
import dnaaaaahtac.wooriforei.domain.comment.dto.CommentResponseDTO;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class BoardResponseDTO {

    private Long boardId;

    private String title;

    private String content;

    private List<String> accessUrls;

    private Long userId;

    private String nickname;

    private int boardLike;

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;

    private List<CommentResponseDTO> comments;

    public BoardResponseDTO(Board board, List<CommentResponseDTO> comments) {
        this.boardId = board.getBoardId();
        this.title = board.getTitle();
        this.content = board.getContent();
        this.accessUrls = board.getBoardImage()==null || board.getBoardImage().isEmpty()?
                null:board.getBoardImage().stream()
                .map(BoardImage::getAccessUrl)
                .collect(Collectors.toList());
        this.userId = board.getUser().getUserId();
        this.nickname = board.getUser().getNickname();
        this.boardLike = board.getBoardLike();
        this.createdAt = board.getCreatedAt();
        this.modifiedAt = board.getModifiedAt();
        this.comments = comments;
    }

    public BoardResponseDTO(Board board) {
        this.boardId = board.getBoardId();
        this.title = board.getTitle();
        this.content = board.getContent();
        this.accessUrls = board.getBoardImage()==null || board.getBoardImage().isEmpty()?
                null:board.getBoardImage().stream()
                .map(BoardImage::getAccessUrl)
                .collect(Collectors.toList());
        this.userId = board.getUser().getUserId();
        this.nickname = board.getUser().getNickname();
        this.boardLike = board.getBoardLike();
        this.createdAt = board.getCreatedAt();
        this.modifiedAt = board.getModifiedAt();
    }

}
