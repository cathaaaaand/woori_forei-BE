package dnaaaaahtac.wooriforei.domain.openapi.dto.restaurant;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.ArrayList;

@Getter
public class RestaurantResponseVO {

    @JsonProperty("list_total_count")
    int listTotalCount;

    @JsonProperty("row")
    ArrayList<RestaurantDetailDTO> row;
}
