package dnaaaaahtac.wooriforei.domain.openapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InformationResponseWrapper {

    @JsonProperty("TbTourInformation")
    private InformationResponseVo tbTourInformation;
}