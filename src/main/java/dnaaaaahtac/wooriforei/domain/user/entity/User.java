package dnaaaaahtac.wooriforei.domain.user.entity;

import dnaaaaahtac.wooriforei.global.auditing.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.Date;

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
    private Boolean isAgreed;

    @Column(nullable = false)
    private Boolean isAuthenticated;

    @Column(length = 300)
    private String introduction;

    @Column(length = 4)
    private String mbti;

    private Date birthday;

    @Column(length = 100)
    private String nation;
}
