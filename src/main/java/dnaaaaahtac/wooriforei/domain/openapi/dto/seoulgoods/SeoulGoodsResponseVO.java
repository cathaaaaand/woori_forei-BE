package dnaaaaahtac.wooriforei.domain.openapi.dto.seoulgoods;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.ArrayList;

@Getter
public class SeoulGoodsResponseVO {

    @JsonProperty("list_total_count")
    int listTotalCount;

    @JsonProperty("row")
    ArrayList<SeoulGoodsDetailDTO> row;

}
