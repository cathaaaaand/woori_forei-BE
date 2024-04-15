package dnaaaaahtac.wooriforei.domain.openapi.dto.hotel;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class HotelResponseDTO {

    @JsonProperty("SebcHotelListKor")
    HotelResponseVO SebcHotelListKor;
}
