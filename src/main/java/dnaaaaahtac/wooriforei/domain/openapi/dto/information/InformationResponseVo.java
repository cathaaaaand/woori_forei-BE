package dnaaaaahtac.wooriforei.domain.openapi.dto.information;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InformationResponseVo {

    @JsonProperty("list_total_count")
    int listTotalCount;

    @JsonProperty("row")
    ArrayList<InformationDetailDto> row;

}
