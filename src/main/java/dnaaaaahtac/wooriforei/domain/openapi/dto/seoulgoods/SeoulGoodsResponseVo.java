package dnaaaaahtac.wooriforei.domain.openapi.dto.seoulgoods;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SeoulGoodsResponseVo {

    @JsonProperty("list_total_count")
    int listTotalCount;

    @JsonProperty("row")
    ArrayList<SeoulGoodsDetailDto> row;

}
