package dnaaaaahtac.wooriforei.domain.openapi.dto.activity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class ActivityResponseDto {

    @JsonProperty("ListPublicReservationCulture")
    ActivityResponseVo listPublicReservationCulture;

}
