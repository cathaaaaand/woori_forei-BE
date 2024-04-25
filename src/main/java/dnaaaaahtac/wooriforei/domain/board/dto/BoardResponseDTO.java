package dnaaaaahtac.wooriforei.domain.board.dto;

import dnaaaaahtac.wooriforei.domain.board.entity.Board;
import dnaaaaahtac.wooriforei.domain.board.entity.BoardImage;
import dnaaaaahtac.wooriforei.domain.user.entity.User;
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

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;

    public BoardResponseDTO(Board board) {
        this.boardId = board.getBoardId();
        this.title = board.getTitle();
        this.content = board.getContent();
        this.accessUrls = board.getBoardImage().stream()
                .map(BoardImage::getAccessUrl)
                .collect(Collectors.toList());
        User user = board.getUser();
        this.userId = user.getUserId();
        this.nickname = user.getNickname();
        this.createdAt = board.getCreatedAt();
        this.modifiedAt = board.getModifiedAt();
    }

}
