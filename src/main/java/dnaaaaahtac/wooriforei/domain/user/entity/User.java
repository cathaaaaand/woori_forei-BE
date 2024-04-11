package dnaaaaahtac.wooriforei.domain.user.entity;

import dnaaaaahtac.wooriforei.global.auditing.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
@Table(name = "users")
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

    @Column(length = 100)
    private String image;

    @Column(length = 50, nullable = false)
    private String username;

    @Column(length = 50, nullable = false)
    private String nickname;

    @Column(length = 200, nullable = false)
    private String userEmail;

    @Column(length = 128, nullable = false)
    private String password;

    @Transient
    private String checkPassword;

    @Column(nullable = false)
    private Boolean isAgreed = false;

    @Column(nullable = false)
    private Boolean isAuthenticated = false;

    @Column(length = 300)
    private String introduction;

    @Column(length = 4)
    private String mbti;

    private String birthday;

    @Column(length = 100)
    private String nation;

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void setImage(String image) {
        this.image = image;
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

    public void setPassword(String password) {
        this.password = password;
    }

    public void setCheckPassword(String checkPassword) {
        this.checkPassword = checkPassword;
    }

    public void setAgreed(Boolean agreed) {
        isAgreed = agreed;
    }

    public void setAuthenticated(Boolean authenticated) {
        isAuthenticated = authenticated;
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
}
