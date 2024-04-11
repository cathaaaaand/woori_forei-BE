package dnaaaaahtac.wooriforei.domain.openapi.dto.seoulgoods;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.ArrayList;

@Getter
public class SeoulGoodsResponseVo {

    @JsonProperty("list_total_count")
    int listTotalCount;

    @JsonProperty("row")
    ArrayList<SeoulGoodsDetailDto> row;

}
