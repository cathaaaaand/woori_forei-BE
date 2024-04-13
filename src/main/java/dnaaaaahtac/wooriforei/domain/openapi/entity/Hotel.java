package dnaaaaahtac.wooriforei.domain.openapi.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Hotel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String mainKey;

    @Column
    private String cate3Name;

    @Column
    private String nameKor;

    @Column
    private String hKorCity;

    @Column
    private String hKorGu;

    @Column
    private String hKorDong;

}
