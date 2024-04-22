package dnaaaaahtac.wooriforei.domain.auth.dto;

import lombok.Getter;

@Getter
public class GoogleLoginRequestDTO {

    private String tokenId;

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }
}
