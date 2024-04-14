package dnaaaaahtac.wooriforei.domain.openapi.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Activity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long activityId;

    @Column
    private String svcid;

    @Column
    private String minclassnm;

    @Column
    private String svcstatnm;

    @Column
    private String svcnm;

    @Column
    private String payatnm;

    @Column
    private String placenm;

    @Column
    private String usetgtinfo;

    @Column
    private String svcurl;

    @Column
    private String x;

    @Column
    private String y;

    @Column
    private String svcopnbgndt;

    @Column
    private String svcopnenddt;

    @Column
    private String rcptbgndt;

    @Column
    private String rcptenddt;

    @Column
    private String imgurl;

    @Column
    private String vmin;

    @Column
    private String vmax;

    @Column
    private String revstddaynm;

    @Column
    private String revstdday;

}
