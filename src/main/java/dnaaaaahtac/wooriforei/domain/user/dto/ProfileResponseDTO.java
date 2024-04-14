package dnaaaaahtac.wooriforei.domain.user.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProfileResponseDTO {

    private Long userId;

    private String username;

    private String nickname;

    private String email;

    private String introduction;

    private String mbti;

    private String birthday;

    private String nation;

    private Long schedulerId;   // 스케줄러 ID (미구현)

    private Long boardId;       // 보드 ID (미구현)

    private Long commentId;     // 댓글 ID (미구현)

    private String image;       // 이미지 URL (미구현)

    private Boolean isAdmin;

    private String phoneNumber;
}
