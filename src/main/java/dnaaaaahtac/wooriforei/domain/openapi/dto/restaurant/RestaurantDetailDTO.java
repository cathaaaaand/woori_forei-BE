package dnaaaaahtac.wooriforei.domain.openapi.dto.restaurant;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class RestaurantDetailDTO {

    @JsonProperty("POST_SN")
    String postSn;

    @JsonProperty("LANG_CODE_ID")
    String langCodeId;

    @JsonProperty("POST_SJ")
    String postSj;

    @JsonProperty("ADDRESS")
    String address;

    @JsonProperty("NEW_ADDRESS")
    String newAddress;

    @JsonProperty("CMMN_TELNO")
    String cmmnTelNo;

    @JsonProperty("CMMN_HMPG_URL")
    String cmmnHmpgUrl;

    @JsonProperty("CMMN_USE_TIME")
    String cmmnUseTime;

    @JsonProperty("SUBWAY_INFO")
    String subwayInfo;

    @JsonProperty("FD_REPRSNT_MENU")
    String fdReprsntMenu;

}

