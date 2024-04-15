package dnaaaaahtac.wooriforei.domain.openapi.dto.landmark;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class LandmarkResponseDTO {

    @JsonProperty("TbVwAttractions")
    LandmarkResponseVO TbVwAttractions;
}
