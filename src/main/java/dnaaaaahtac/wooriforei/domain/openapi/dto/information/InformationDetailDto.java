
package dnaaaaahtac.wooriforei.domain.openapi.dto.information;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class InformationDetailDto {

    @JsonProperty("TRSMICNM")
    private String trsmicnm;

    @JsonProperty("SIGNGUNM")
    private String signgunm;

    @JsonProperty("TRSMICINTRCN")
    private String trsmicintrcn;

    @JsonProperty("ADISVCINFO")
    private String adisvcinfo;

    @JsonProperty("RSTDE")
    private String rstde;

    @JsonProperty("SUMMEROPEROPENHHMM")
    private String summeroperopenhhmm;

    @JsonProperty("SUMMEROPERCLOSEHHMM")
    private String summeroperclosehhmm;

    @JsonProperty("WINTEROPEROPENHHMM")
    private String winteroperopenhhmm;

    @JsonProperty("WINTEROPERCLOSEHHMM")
    private String winteroperclosehhmm;

    @JsonProperty("ENGGUIDANCEYN")
    private String engguidanceyn;

    @JsonProperty("JPGUIDANCEYN")
    private String jpguidanceyn;

    @JsonProperty("CHGUIDANCEYN")
    private String chguidanceyn;

    @JsonProperty("GUIDANCEPHONENUMBER")
    private String guidancephonenumber;

    @JsonProperty("RDNMADR")
    private String rdnmadr;

    @JsonProperty("LNMADR")
    private String lnmadr;

    @JsonProperty("HOMEPAGEURL")
    private String homepageurl;

    @JsonProperty("LATITUDE")
    private String latitude;

    @JsonProperty("LONGITUDE")
    private String longitude;
}