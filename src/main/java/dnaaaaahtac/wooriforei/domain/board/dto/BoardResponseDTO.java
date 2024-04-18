package dnaaaaahtac.wooriforei.domain.board.dto;

import dnaaaaahtac.wooriforei.domain.board.entity.Board;
import dnaaaaahtac.wooriforei.domain.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class BoardResponseDTO {

    private Long boardId;
    private String title;
    private String content;
    private String image;
    private Long userId;
    private String nickname;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public BoardResponseDTO(Board board) {
        this.boardId = board.getBoardId();
        this.title = board.getTitle();
        this.content = board.getContent();
        this.image = board.getImage();
        User user = board.getUser();
        this.userId = user.getUserId();
        this.nickname = user.getNickname();
        this.createdAt = board.getCreatedAt();
        this.modifiedAt = board.getModifiedAt();
    }

}
