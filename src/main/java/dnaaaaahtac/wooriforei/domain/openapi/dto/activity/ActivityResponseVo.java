package dnaaaaahtac.wooriforei.domain.openapi.dto.activity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.ArrayList;

@Getter
public class ActivityResponseVo {

    @JsonProperty("list_total_count")
    String listTotalCount;

    @JsonProperty("row")
    ArrayList<ActivityDetailDto> row;

}
