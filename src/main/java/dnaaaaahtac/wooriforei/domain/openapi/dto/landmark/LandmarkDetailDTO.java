package dnaaaaahtac.wooriforei.domain.openapi.dto.landmark;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class LandmarkDetailDTO {

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
    String cmmnTelno;

    @JsonProperty("CMMN_HMPG_URL")
    String cmmnHmpgUrl;

    @JsonProperty("CMMN_USE_TIME")
    String CmmnUseTime;

    @JsonProperty("CMMN_BSNDE")
    String cmmnBsnde;

    @JsonProperty("CMMN_RSTDE")
    String cmmnRstde;

    @JsonProperty("SUBWAY_INFO")
    String subwayInfo;

    @JsonProperty("BF_DESC")
    String bfDesc;

}
