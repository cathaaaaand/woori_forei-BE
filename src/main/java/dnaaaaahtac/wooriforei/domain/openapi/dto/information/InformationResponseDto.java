package dnaaaaahtac.wooriforei.domain.openapi.dto.information;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InformationResponseDto {

    @JsonProperty("TbTourInformation")
    private InformationResponseVo tbTourInformation;
}