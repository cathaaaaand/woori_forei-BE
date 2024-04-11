package dnaaaaahtac.wooriforei.domain.auth.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoginAdminResponseDTO {

    private Long adminId;
    private String adminName;
    private String adminEmail;
    private String phoneNumber;
}
