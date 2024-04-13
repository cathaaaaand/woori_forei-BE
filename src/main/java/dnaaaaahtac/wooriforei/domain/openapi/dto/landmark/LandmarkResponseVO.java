package dnaaaaahtac.wooriforei.domain.openapi.dto.landmark;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.ArrayList;

@Getter
public class LandmarkResponseVO {

    @JsonProperty("list_total_count")
    int listTotalCount;

    @JsonProperty("row")
    ArrayList<LandmarkDetailDTO> row;
}
