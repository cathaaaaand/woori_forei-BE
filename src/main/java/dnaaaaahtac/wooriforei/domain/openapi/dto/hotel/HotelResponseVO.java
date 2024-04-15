package dnaaaaahtac.wooriforei.domain.openapi.dto.hotel;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.ArrayList;

@Getter
public class HotelResponseVO {

    @JsonProperty("list_total_count")
    int listTotalCount;

    @JsonProperty("row")
    ArrayList<HotelDetailDTO> row;

}
