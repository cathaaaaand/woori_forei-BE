package dnaaaaahtac.wooriforei.domain.openapi.dto.seoulgoods;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class SeoulGoodsResponseDTO {

    @JsonProperty("frgnrTourGiftShopInfo")
    private SeoulGoodsResponseVO frgnrtourgiftshopinfo;

}
