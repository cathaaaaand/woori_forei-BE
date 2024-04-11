package dnaaaaahtac.wooriforei.domain.openapi.dto.seoulgoods;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SeoulGoodsResponseDto {

    @JsonProperty("frgnrTourGiftShopInfo")
    private SeoulGoodsResponseVo frgnrtourgiftshopinfo;

}
