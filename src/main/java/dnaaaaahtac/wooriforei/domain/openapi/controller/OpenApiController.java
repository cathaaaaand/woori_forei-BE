package dnaaaaahtac.wooriforei.domain.openapi.controller;


import dnaaaaahtac.wooriforei.domain.openapi.dto.activity.ActivityResponseDTO;
import dnaaaaahtac.wooriforei.domain.openapi.dto.information.InformationResponseDTO;
import dnaaaaahtac.wooriforei.domain.openapi.dto.seoulgoods.SeoulGoodsResponseDTO;
import dnaaaaahtac.wooriforei.domain.openapi.entity.Activity;
import dnaaaaahtac.wooriforei.domain.openapi.entity.Information;
import dnaaaaahtac.wooriforei.domain.openapi.entity.SeoulGoods;
import dnaaaaahtac.wooriforei.domain.openapi.service.ActivityService;
import dnaaaaahtac.wooriforei.domain.openapi.service.InformationService;
import dnaaaaahtac.wooriforei.domain.openapi.service.SeoulGoodsService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;


@RestController
@AllArgsConstructor
@RequestMapping("/api/openAPI")
public class OpenApiController {

    private final InformationService informationService;
    private final SeoulGoodsService seoulGoodsService;
    private final ActivityService activityService;

    //information 호출
    @GetMapping("/informations")
    public Mono<ResponseEntity<InformationResponseDTO>> information() {

        return informationService.retrieveInformation()
                .map(response -> ResponseEntity.ok().body(response));
    }

    // information 전체 조회
    @GetMapping("/informations/check")
    public ResponseEntity<List<Information>> checkAllInformations() {

        List<Information> informations = informationService.findAllInformations();

        return ResponseEntity.ok().body(informations);
    }

    // information 단일 조회
    @GetMapping("/informations/{informationId}/check")
    public Mono<ResponseEntity<Information>> checkInformationById(@PathVariable Long informationId) {

        return informationService.findInformationById(informationId)
                .map(information -> ResponseEntity.ok().body(information));
    }

    //seoulgoods 호출
    @GetMapping("/seoulgoods")
    public Mono<ResponseEntity<SeoulGoodsResponseDTO>> seoulGoods() {

        return seoulGoodsService.retrieveSeoulGoods()
                .map(response -> ResponseEntity.ok().body(response));
    }

    // seoulgoods 전체 조회
    @GetMapping("/seoulgoods/check")
    public ResponseEntity<List<SeoulGoods>> checkAllSeoulGoods() {

        List<SeoulGoods> seoulGoods = seoulGoodsService.findAllSeoulGoods();

        return ResponseEntity.ok().body(seoulGoods);
    }

    // seoulgoods 단일 조회
    @GetMapping("/seoulgoods/{seoulgoodsId}/check")
    public Mono<ResponseEntity<SeoulGoods>> checkSeoulGoodsById(@PathVariable Long seoulgoodsId) {

        return seoulGoodsService.findInSeoulGoodsById(seoulgoodsId)
                .map(seoulGoods -> ResponseEntity.ok().body(seoulGoods));
    }

    //activity 호출
    @GetMapping("/activities")
    public Mono<ResponseEntity<ActivityResponseDTO>> activities(){

        return activityService.retrieveActivity()
                .map(response -> ResponseEntity.ok().body(response));
    }

    //activity 전체 조회
    @GetMapping("/activities/check")
    public ResponseEntity<List<Activity>> checkAllActivities(){

        List<Activity> activities = activityService.findAllActivity();

        return ResponseEntity.ok().body(activities);
    }

    //activity 단일 조회
    @GetMapping("/activities/{activitiesId}/check")
    public Mono<ResponseEntity<Activity>> checkActivitiesById(@PathVariable Long activitiesId) {

        return activityService.findActivityById(activitiesId)
                .map(activity -> ResponseEntity.ok().body(activity));
    }
}
