package dnaaaaahtac.wooriforei.domain.auth.dto;

import lombok.Getter;

@Getter
public class VerificationEmailCodeRequestDTO {

    private String email;

    private String verificationCode;
}
