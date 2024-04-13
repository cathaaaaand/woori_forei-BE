package dnaaaaahtac.wooriforei.domain.auth.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoginUserResponseDTO {

    private Long userId;

    private String username;

    private String nickname;

    private String email;

    private String introduction;

    private String mbti;

    private String birthday;

    private String nation;
}
