package dnaaaaahtac.wooriforei.domain.faq.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class FaqResponseDTO {

    private Long faqId;

    private String faqTitle;

    private String faqContent;

    private Long userId;

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;

    public void setFaqId(Long faqId) {
        this.faqId = faqId;
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

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setModifiedAt(LocalDateTime modifiedAt) {
        this.modifiedAt = modifiedAt;
    }
}
