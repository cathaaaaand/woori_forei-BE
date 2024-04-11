package dnaaaaahtac.wooriforei.domain.openapi.dto.seoulgoods;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;

@Getter
public class SeoulGoodsResponseDto {

    @JsonProperty("frgnrTourGiftShopInfo")
    private SeoulGoodsResponseVo frgnrtourgiftshopinfo;

}
