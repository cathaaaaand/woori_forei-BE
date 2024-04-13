package dnaaaaahtac.wooriforei.domain.user.dto;

import lombok.Getter;
import lombok.Value;

@Getter
@Value
public class PasswordUpdateRequestDTO {

    String password;

    String updatePassword;

    String checkUpdatePassword;
}
