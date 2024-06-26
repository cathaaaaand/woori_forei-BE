package dnaaaaahtac.wooriforei.domain.auth.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoginResponseDTO {

    private Long userId;

    private String image;

    private String username;

    private String nickname;

    private String email;

    private String introduction;

    private String mbti;

    private String birthday;

    private String nation;

    private Boolean isAdmin;

    private Boolean isAgreed;

    private String jwtToken;

    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }
}
