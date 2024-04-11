package dnaaaaahtac.wooriforei.domain.openapi.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class SeoulGoods {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String nm;

    @Column
    private String addr;

    @Column
    private String state;

    @Column
    private String stopDt;

    @Column
    private String suspensionStartDt;

    @Column
    private String suspensionEndDt;

    @Column
    private String reOpenDt;

    @Column
    private String tel;

    @Column
    private String xcode;

    @Column
    private String ycode;

}
