package dnaaaaahtac.wooriforei.domain.auth.entity;


import dnaaaaahtac.wooriforei.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "emails")
public class EmailVerification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long emailId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "userId")
    private User userId;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String verificationCode;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime expiresAt;

    @Column(nullable = false)
    private Boolean verified = false;

    @Column(nullable = false, columnDefinition = "int default 0")
    private int sendAttempts;

    public void setEmailId(Long emailId) {
        this.emailId = emailId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setExpiresAt(LocalDateTime expiresAt) {
        this.expiresAt = expiresAt;
    }

    public void setVerified(Boolean verified) {
        this.verified = verified;
    }

    public void incrementSendAttempts() {
        this.sendAttempts++;
    }
}
