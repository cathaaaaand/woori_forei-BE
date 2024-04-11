package dnaaaaahtac.wooriforei.domain.openapi.dto.seoulgoods;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SeoulGoodsDetailDto {

    @JsonProperty("NM")
    private String nm;

    @JsonProperty("ADDR")
    private String addr;

    @JsonProperty("STATE")
    private String state;

    @JsonProperty("STOP_DT")
    private String stopDt;

    @JsonProperty("SUSPENSION_START_DT")
    private String suspensionStartDt;

    @JsonProperty("SUSPENSION_END_DT")
    private String suspensionEndDt;

    @JsonProperty("RE_OPEN_DT")
    private String reOpenDt;

    @JsonProperty("TEL")
    private String tel;

    @JsonProperty("XCODE")
    private String xcode;

    @JsonProperty("YCODE")
    private String ycode;

}
