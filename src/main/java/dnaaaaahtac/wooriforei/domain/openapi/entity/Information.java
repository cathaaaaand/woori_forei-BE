package dnaaaaahtac.wooriforei.domain.openapi.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Information {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String trsmicnm;

    @Column
    private String signgunm;

    @Column
    private String trsmicintrcn;

    @Column
    private String adisvcinfo;

    @Column
    private String rstde;

    @Column
    private String summeroperopenhhmm;

    @Column
    private String summeroperclosehhmm;

    @Column
    private String winteroperopenhhmm;

    @Column
    private String winteroperclosehhmm;

    @Column
    private String engguidanceyn;

    @Column
    private String jpguidanceyn;

    @Column
    private String chguidanceyn;

    @Column
    private String guidancephonenumber;

    @Column
    private String rdnmadr;

    @Column
    private String lnmadr;

    @Column
    private String homepageurl;

    @Column
    private String latitude;

    @Column
    private String longitude;

}