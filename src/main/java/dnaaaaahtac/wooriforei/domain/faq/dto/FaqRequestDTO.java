package dnaaaaahtac.wooriforei.domain.faq.dto;

import lombok.Getter;

@Getter
public class FaqRequestDTO {

    private String faqTitle;

    private String faqContent;

    public void setFaqTitle(String faqTitle) {
        this.faqTitle = faqTitle;
    }

    public void setFaqContent(String faqContent) {
        this.faqContent = faqContent;
    }
}
