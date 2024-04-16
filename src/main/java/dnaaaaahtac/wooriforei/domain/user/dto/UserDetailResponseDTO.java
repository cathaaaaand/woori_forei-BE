package dnaaaaahtac.wooriforei.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailResponseDTO {

    private Long userId;
    private String username;
    private String nickname;
    private String userEmail;

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
}
