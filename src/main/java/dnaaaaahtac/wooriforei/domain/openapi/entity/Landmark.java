package dnaaaaahtac.wooriforei.domain.openapi.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Landmark {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String postSn;

    @Column
    private String langCodeId;

    @Column
    private String postSj;

    @Column
    private String address;

    @Column
    private String newAddress;

    @Column
    private String cmmnTelno;

    @Column
    private String cmmnHmpgUrl;

    @Column
    private String CmmnUseTime;

    @Column
    private String cmmnBsnde;

    @Column
    private String cmmnRstde;

    @Column
    private String subwayInfo;

    @Column
    private String bfDesc;

}
