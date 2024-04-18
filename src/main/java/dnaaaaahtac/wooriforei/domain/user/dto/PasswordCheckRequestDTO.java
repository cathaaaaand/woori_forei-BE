package dnaaaaahtac.wooriforei.domain.user.dto;

import lombok.Getter;

@Getter
public class PasswordCheckRequestDTO {

    private String password;

    public void setPassword(String password) {
        this.password = password;
    }
}
