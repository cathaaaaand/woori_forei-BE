package dnaaaaahtac.wooriforei.domain.openapi.dto.information;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.ArrayList;

@Getter
public class InformationResponseVo {

    @JsonProperty("list_total_count")
    int listTotalCount;

    @JsonProperty("row")
    ArrayList<InformationDetailDto> row;

}
