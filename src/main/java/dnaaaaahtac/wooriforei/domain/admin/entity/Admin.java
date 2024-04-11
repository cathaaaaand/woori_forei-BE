package dnaaaaahtac.wooriforei.domain.admin.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
@Table(name = "admins")
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long adminId;

    @Column(length = 100, nullable = false)
    private String adminName;

    @Column(length = 200, nullable = false)
    private String adminEmail;

    @Column(length = 128, nullable = false)
    private String password;

    @Transient
    private String checkPassword;

    @Column(nullable = false)
    private boolean isAgreed = false;

    @Column(nullable = false)
    private boolean isAuthenticated = false;

    @Column(nullable = false)
    private String phoneNumber;

    public void setAdminId(Long adminId) {
        this.adminId = adminId;
    }

    public void setAdminEmail(String adminEmail) {
        this.adminEmail = adminEmail;
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

    public void setAgreed(boolean agreed) {
        isAgreed = agreed;
    }

    public void setAuthenticated(boolean authenticated) {
        isAuthenticated = authenticated;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }
}
