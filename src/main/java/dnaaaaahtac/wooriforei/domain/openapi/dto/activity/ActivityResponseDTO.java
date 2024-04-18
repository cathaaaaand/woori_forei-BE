package dnaaaaahtac.wooriforei.domain.openapi.dto.activity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class ActivityResponseDTO {

    @JsonProperty("ListPublicReservationCulture")
    ActivityResponseVO listPublicReservationCulture;

}
