package dnaaaaahtac.wooriforei.domain.openapi.dto.restaurant;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class RestaurantResponseDTO {

    @JsonProperty("TbVwRestaurants")
    RestaurantResponseVO TbVwRestaurants;
}
