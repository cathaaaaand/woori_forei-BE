package dnaaaaahtac.wooriforei.domain.openapi.dto.hotel;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class HotelDetailDTO {

    @JsonProperty("MAIN_KEY")
    String mainKey;

    @JsonProperty("CATE3_NAME")
    String cate3Name;

    @JsonProperty("NAME_KOR")
    String nameKor;

    @JsonProperty("H_KOR_CITY")
    String hKorCity;

    @JsonProperty("H_KOR_GU")
    String hKorGu;

    @JsonProperty("H_KOR_DONG")
    String hKorDong;

}
