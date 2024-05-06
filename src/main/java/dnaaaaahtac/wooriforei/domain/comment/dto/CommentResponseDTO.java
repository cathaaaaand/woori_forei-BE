package dnaaaaahtac.wooriforei.domain.comment.dto;

import dnaaaaahtac.wooriforei.domain.comment.entity.Comment;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentResponseDTO {

    private Long commentId;

    private Long boardId;

    private String username;

    private String commentContent;

    private Long userId;

    private LocalDateTime createAt;

    private LocalDateTime modifiedAt;

    public CommentResponseDTO(Comment comment) {
        this.commentId = comment.getCommentId();
        this.boardId = comment.getBoard().getBoardId();
        this.username = comment.getUser().getUsername();
        this.commentContent = comment.getContent();
        this.userId = comment.getUser().getUserId();
        this.createAt = comment.getCreateAt();
        this.modifiedAt = comment.getModifiedAt();
    }
}
