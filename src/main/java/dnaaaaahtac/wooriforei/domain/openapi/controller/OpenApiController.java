package dnaaaaahtac.wooriforei.domain.openapi.controller;


import dnaaaaahtac.wooriforei.domain.openapi.dto.information.InformationResponseDto;
import dnaaaaahtac.wooriforei.domain.openapi.dto.seoulgoods.SeoulGoodsResponseDto;
import dnaaaaahtac.wooriforei.domain.openapi.entity.Information;
import dnaaaaahtac.wooriforei.domain.openapi.entity.SeoulGoods;
import dnaaaaahtac.wooriforei.domain.openapi.service.InformationService;
import dnaaaaahtac.wooriforei.domain.openapi.service.SeoulGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;


@RestController
@RequestMapping("/api/openAPI")
public class OpenApiController {

    private final InformationService informationService;
    private final SeoulGoodsService seoulGoodsService;

    @Autowired
    public OpenApiController(InformationService informationService, SeoulGoodsService seoulGoodsService) {
        this.informationService = informationService;
        this.seoulGoodsService = seoulGoodsService;
    }

    //information 호출
    @GetMapping("/informations")
    public Mono<ResponseEntity<InformationResponseDto>> information() {

        return informationService.retrieveInformation()
                .map(response -> ResponseEntity.ok().body(response));
    }

    // information 전체 조회
    @GetMapping("/informations/check")
    public ResponseEntity<List<Information>> checkAllInformations() {

        List<Information> informations = informationService.findAllInformations();
        return ResponseEntity.ok().body(informations);
    }

    // information 단건 조회
    @GetMapping("/informations/{informationId}/check")
    public Mono<ResponseEntity<Information>> checkInformationById(@PathVariable Long informationId) {

        return informationService.findInformationById(informationId)
                .map(information -> ResponseEntity.ok().body(information));
    }

    //seoulgoods 호출
    @GetMapping("/seoulgoods")
    public Mono<ResponseEntity<SeoulGoodsResponseDto>> seoulGoods() {

        return seoulGoodsService.retrieveSeoulGoods()
                .map(response -> ResponseEntity.ok().body(response));
    }

    // seoulgoods 전체 조회
    @GetMapping("/seoulgoods/check")
    public ResponseEntity<List<SeoulGoods>> checkAllseoulGoods() {

        List<SeoulGoods> seoulGoods = seoulGoodsService.findAllSeoulGoods();
        return ResponseEntity.ok().body(seoulGoods);
    }

    // seoulgoods 단건 조회
    @GetMapping("/seoulgoods/{seoulgoodsId}/check")
    public Mono<ResponseEntity<SeoulGoods>> checkseoulGoodsById(@PathVariable Long seoulgoodsId) {

        return seoulGoodsService.findInSeoulGoodsById(seoulgoodsId)
                .map(seoulGoods -> ResponseEntity.ok().body(seoulGoods));
    }

}
