package dnaaaaahtac.wooriforei.domain.auth.entity;

import dnaaaaahtac.wooriforei.domain.user.entity.User;
import dnaaaaahtac.wooriforei.global.auditing.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
@Table(name = "SocialLogins")
public class SocialLogin extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long socialLoginId;

    @ManyToOne
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    private User user;

    private String socialType;

    private String externalId;

    public void setSocialLoginId(Long socialLoginId) {
        this.socialLoginId = socialLoginId;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setSocialType(String socialType) {
        this.socialType = socialType;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }
}
