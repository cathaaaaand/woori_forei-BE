package dnaaaaahtac.wooriforei.domain.faq.entity;

import dnaaaaahtac.wooriforei.global.auditing.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
@Table(name = "faqs")
public class Faq extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String faqTitle;

    @Column(nullable = false)
    private String faqContent;

    @Column(nullable = false)
    private Long userId;

    public void setId(Long id) {
        this.id = id;
    }

    public void setFaqTitle(String faqTitle) {
        this.faqTitle = faqTitle;
    }

    public void setFaqContent(String faqContent) {
        this.faqContent = faqContent;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
