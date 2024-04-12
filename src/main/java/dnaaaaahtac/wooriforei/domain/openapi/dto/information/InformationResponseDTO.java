package dnaaaaahtac.wooriforei.domain.openapi.dto.information;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class InformationResponseDTO {

    @JsonProperty("TbTourInformation")
    private InformationResponseVO tbTourInformation;
}