package dnaaaaahtac.wooriforei.domain.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class RegisterUserRequestDTO {

    @NotBlank
    private String username;

    @NotBlank
    private String nickname;

    @NotBlank
    @Email
    @Pattern(regexp = "^[a-zA-Z0-9]+@[a-zA-Z0-9]+(\\.[a-zA-Z]{2,})+$",
            message = "올바른 이메일 형식이 아닙니다. 문자(대문자/소문자)@도메인으로 입력해주세요.")
    private String email;

    @NotBlank
    @Size(min = 8)
    private String password;

    @NotBlank
    @Size(min = 8)
    private String checkPassword;

    private String introduction;

    private String mbti;

    private String birthday;

    private String nation;

    private String image;

    private Boolean isAgreed;

    public void setAgreed(Boolean agreed) {
        isAgreed = agreed;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setCheckPassword(String checkPassword) {
        this.checkPassword = checkPassword;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public void setMbti(String mbti) {
        this.mbti = mbti;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
