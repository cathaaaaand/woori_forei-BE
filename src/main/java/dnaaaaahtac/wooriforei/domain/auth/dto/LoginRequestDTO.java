package dnaaaaahtac.wooriforei.domain.auth.dto;

import lombok.Getter;

@Getter
public class LoginRequestDTO {

    private String email;
    private String password;

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
