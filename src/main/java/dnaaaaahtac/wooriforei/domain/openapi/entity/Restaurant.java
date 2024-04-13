package dnaaaaahtac.wooriforei.domain.openapi.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column
    String postSn;

    @Column
    String langCodeId;

    @Column
    String postSj;

    @Column
    String address;

    @Column
    String newAddress;

    @Column
    String cmmnTelno;

    @Column
    String cmmnHmpgUrl;

    @Column
    String cmmnUseTime;

    @Column
    String subwayInfo;

    @Column
    String fdReprsntMenu;

}
