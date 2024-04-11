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
    private String phone;

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

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setAgreed(boolean agreed) {
        isAgreed = agreed;
    }

    public void setAuthenticated(boolean authenticated) {
        isAuthenticated = authenticated;
    }
}
