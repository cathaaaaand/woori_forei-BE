package dnaaaaahtac.wooriforei.domain.user.dto;

import dnaaaaahtac.wooriforei.domain.image.entity.Image;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class ProfileResponseDTO {

    private Long userId;

    private String username;

    private String nickname;

    private String email;

    private String description;

    private String mbti;

    private String birthday;

    private String nation;

    private List<SchedulerDTO> schedulers;

    private List<BoardDTO> boards;

    private List<CommentDTO> comments;

    private Image image;

    private Boolean isAdmin;

    @Getter
    @Builder
    public static class SchedulerDTO {
        private Long schedulerId;
        private String schedulerName;
    }

    @Getter
    @Builder
    public static class BoardDTO {
        private Long boardId;
        private String boardTitle;
    }

    @Getter
    @Builder
    public static class CommentDTO {
        private Long commentId;
        private String commentContent;
    }
}
