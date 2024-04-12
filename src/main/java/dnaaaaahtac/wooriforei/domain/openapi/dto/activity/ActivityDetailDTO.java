package dnaaaaahtac.wooriforei.domain.openapi.dto.activity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class ActivityDetailDTO {

    @JsonProperty("SVCID")
    String svcid;

    @JsonProperty("MINCLASSNM")
    String minclassnm;

    @JsonProperty("SVCSTATNM")
    String svcstatnm;

    @JsonProperty("SVCNM")
    String svcnm;

    @JsonProperty("PAYATNM")
    String payatnm;

    @JsonProperty("PLACENM")
    String placenm;

    @JsonProperty("USETGTINFO")
    String usetgtinfo;

    @JsonProperty("SVCURL")
    String svcurl;

    @JsonProperty("X")
    String x;

    @JsonProperty("Y")
    String y;

    @JsonProperty("SVCOPNBGNDT")
    String svcopnbgndt;

    @JsonProperty("SVCOPNENDDT")
    String svcopnenddt;

    @JsonProperty("RCPTBGNDT")
    String rcptbgndt;

    @JsonProperty("RCPTENDDT")
    String rcptenddt;

    @JsonProperty("IMGURL")
    String imgurl;

    @JsonProperty("V_MIN")
    String vmin;

    @JsonProperty("V_MAX")
    String vmax;

    @JsonProperty("REVSTDDAYNM")
    String revstddaynm;

    @JsonProperty("REVSTDDAY")
    String revstdday;

}
