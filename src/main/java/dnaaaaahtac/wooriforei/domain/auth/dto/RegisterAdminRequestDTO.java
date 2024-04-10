package dnaaaaahtac.wooriforei.domain.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class RegisterAdminRequestDTO {

    @NotBlank
    @Email
    @Pattern(regexp = "^[a-zA-Z0-9]+@[a-zA-Z0-9]+(\\.[a-zA-Z]{2,})+$",
            message = "올바른 이메일 형식이 아닙니다. 문자(대문자/소문자)@도메인으로 입력해주세요.")
    private String email;

    @NotBlank
    private String password;

    @NotBlank
    private String checkPassword;

    @NotBlank
    private String phoneNumber;

    @NotBlank
    private String secretCode;

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setCheckPassword(String checkPassword) {
        this.checkPassword = checkPassword;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setSecretCode(String secretCode) {
        this.secretCode = secretCode;
    }
}
