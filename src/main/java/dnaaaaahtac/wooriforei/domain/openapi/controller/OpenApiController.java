package dnaaaaahtac.wooriforei.domain.openapi.controller;


import dnaaaaahtac.wooriforei.domain.openapi.dto.activity.ActivityResponseDTO;
import dnaaaaahtac.wooriforei.domain.openapi.dto.hotel.HotelResponseDTO;
import dnaaaaahtac.wooriforei.domain.openapi.dto.information.InformationResponseDTO;
import dnaaaaahtac.wooriforei.domain.openapi.dto.landmark.LandmarkResponseDTO;
import dnaaaaahtac.wooriforei.domain.openapi.dto.restaurant.RestaurantResponseDTO;
import dnaaaaahtac.wooriforei.domain.openapi.dto.seoulgoods.SeoulGoodsResponseDTO;
import dnaaaaahtac.wooriforei.domain.openapi.entity.*;
import dnaaaaahtac.wooriforei.domain.openapi.service.*;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/openAPI")
public class OpenApiController {

    private final InformationService informationService;
    private final SeoulGoodsService seoulGoodsService;
    private final ActivityService activityService;
    private final HotelService hotelService;
    private final LandmarkService landmarkService;
    private final RestaurantService restaurantService;

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
                .map(response -> ResponseEntity.ok().body(response));
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
                .map(response -> ResponseEntity.ok().body(response));
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
    public Mono<ResponseEntity<Activity>> checkActivitiesById (@PathVariable Long activitiesId) {

        return activityService.findActivityById(activitiesId)
                .map(response -> ResponseEntity.ok().body(response));
    }

    @GetMapping("/hotels")
    public Mono<ResponseEntity<HotelResponseDTO>> hotels(){

        return hotelService.retrieveHotel()
                .map(response -> ResponseEntity.ok().body(response));
    }

    @GetMapping("/hotels/check")
    public ResponseEntity<List<Hotel>> checkALLHotels(){

        List<Hotel> hotels = hotelService.findAllHotels();

        return ResponseEntity.ok().body(hotels);
    }

    @GetMapping("/hotels/{hotelId}/check")
    public Mono<ResponseEntity<Hotel>> checkHotelsById (@PathVariable Long hotelId){

        return hotelService.findHotelById(hotelId)
                .map(response -> ResponseEntity.ok().body(response));
    }

    @GetMapping("/landmarks")
    public Mono<ResponseEntity<List<LandmarkResponseDTO>>> landmarks() {
        return landmarkService.retrieveLandmarkPage()
                .collectList()  // Flux를 List로 변환
                .map(response -> ResponseEntity.ok().body(response));
    }

    @GetMapping("/landmarks/check")
    public ResponseEntity<List<Landmark>> checkAllLandmark(){

        List<Landmark> landmarks = landmarkService.findAllLandmarks();

        return ResponseEntity.ok().body(landmarks);
    }

    @GetMapping("/landmarks/{landmarkId}/check")
    public Mono<ResponseEntity<Landmark>> checkLandmarkById(@PathVariable Long landmarkId){

        return landmarkService.findLandmarkById(landmarkId)
                .map(reponse -> ResponseEntity.ok().body(reponse));
    }

    @GetMapping("/restaurants")
    public Mono<ResponseEntity<List<RestaurantResponseDTO>>> restaurants() {

        return restaurantService.retrieveRestaurantPage()
                .collectList()
                .map(response ->ResponseEntity.ok().body(response));
    }

    @GetMapping("/restaurants/check")
    public ResponseEntity<List<Restaurant>> checkAllRestaurant() {

        List<Restaurant> restaurants = restaurantService.findAllRestaurant();

        return ResponseEntity.ok().body(restaurants);
    }

    @GetMapping("/restaurants/{restaurantId}/check")
    public Mono<ResponseEntity<Restaurant>> checkRestaurantById (@PathVariable Long restaurantId) {

        return restaurantService.findRestaurantById(restaurantId)
                .map(response -> ResponseEntity.ok().body(response));
    }


}
