package dnaaaaahtac.wooriforei.domain.user.entity;

import dnaaaaahtac.wooriforei.domain.image.entity.Image;
import dnaaaaahtac.wooriforei.global.auditing.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;

@Data
@Entity
@Getter
@Table(name = "users")
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "image_id") // 외래키로 사용될 컬럼 이름
    private Image image;

    @Column(length = 50, nullable = false)
    private String username;

    @Column(length = 50)
    private String nickname;

    @Column(length = 200, nullable = false)
    private String email;

    @Column(length = 128, nullable = false)
    private String password;

    @Transient
    private String checkPassword;

    @Column(nullable = false)
    private Boolean isAgreed = false;

    @Column(nullable = false)
    private Boolean isAuthenticated = false;

    @Column(nullable = false)
    private Boolean isAdmin;

    @Column(length = 300)
    private String introduction;

    @Column(length = 4)
    private String mbti;

    private String birthday;

    @Column(length = 20)
    private String phoneNumber;

    @Column(length = 100)
    private String nation;

    public Long getId() {
        return userId;
    }


    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
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

    public void setAdmin(Boolean admin) {
        isAdmin = admin;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public boolean isAdmin() {
        return isAdmin;
    }
}
