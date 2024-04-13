package dnaaaaahtac.wooriforei.domain.user.dto;

import lombok.Getter;
import lombok.Value;

@Getter
@Value
public class ProfileRequestDTO {

    String username;

    String nickname;

    String email;

    String description;

    String mbti;

    String birthday;

    String nation;

    String image;

    String phoneNumber;
}
