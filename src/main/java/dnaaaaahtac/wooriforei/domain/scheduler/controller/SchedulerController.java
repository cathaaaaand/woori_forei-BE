package dnaaaaahtac.wooriforei.domain.scheduler.controller;

import dnaaaaahtac.wooriforei.domain.scheduler.dto.*;
import dnaaaaahtac.wooriforei.domain.scheduler.service.SchedulerService;
import dnaaaaahtac.wooriforei.global.common.CommonResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(
        origins = {"https://cat.wooriforei.info", "http://localhost:3000", "https://www.wooriforei.info"},
        allowCredentials = "true",
        allowedHeaders = {"Authorization", "Content-Type", "Accept"},
        methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS}
)
@RestController
@RequestMapping("/api/schedulers")
@RequiredArgsConstructor
public class SchedulerController {

    private final SchedulerService schedulerService;

    @PostMapping
    public ResponseEntity<CommonResponse<SchedulerResponseDTO>> createScheduler(
            @RequestBody @Valid SchedulerRequestDTO schedulerRequestDTO) {

        SchedulerResponseDTO schedulerResponse = schedulerService.createScheduler(schedulerRequestDTO);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CommonResponse.of("스케줄러 생성 성공", schedulerResponse));
    }

    @GetMapping("/{schedulerId}")
    public ResponseEntity<CommonResponse<SchedulerResponseDTO>> getScheduler(
            @PathVariable Long schedulerId) {

        SchedulerResponseDTO scheduler = schedulerService.getSchedulerById(schedulerId);

        return ResponseEntity.ok(CommonResponse.of("스케줄러 단일 조회 성공", scheduler));
    }

    @GetMapping
    public ResponseEntity<CommonResponse<List<SchedulerResponseDTO>>> getAllSchedulers() {
        List<SchedulerResponseDTO> schedulers = schedulerService.getAllSchedulers();

        return ResponseEntity.ok(CommonResponse.of("스케줄러 전체 조회 성공", schedulers));
    }

    @PutMapping("/{schedulerId}")
    public ResponseEntity<CommonResponse<SchedulerResponseDTO>> updateScheduler(
            @PathVariable Long schedulerId,
            @RequestBody @Valid SchedulerRequestDTO schedulerRequestDTO) {

        SchedulerResponseDTO updatedScheduler = schedulerService.updateScheduler(schedulerId, schedulerRequestDTO);

        return ResponseEntity.ok(CommonResponse.of("스케줄러 수정 성공", updatedScheduler));
    }

    @DeleteMapping("/{schedulerId}")
    public ResponseEntity<CommonResponse<Void>> deleteScheduler(
            @PathVariable Long schedulerId) {

        schedulerService.deleteScheduler(schedulerId);

        return ResponseEntity.ok(CommonResponse.of("스케줄러 삭제 성공", null));
    }

    @PostMapping("/{schedulerId}/activities")
    public ResponseEntity<CommonResponse<Void>> addActivityToScheduler(
            @PathVariable Long schedulerId,
            @RequestBody @Valid SchedulerActivityRequestDTO activityDTO) {

        schedulerService.addActivityToScheduler(schedulerId, activityDTO);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CommonResponse.of("스케줄러에 문화체험 추가 성공", null));
    }

    @PostMapping("/{schedulerId}/hotels")
    public ResponseEntity<CommonResponse<Void>> addHotelToScheduler(
            @PathVariable Long schedulerId,
            @RequestBody @Valid SchedulerHotelRequestDTO hotelDTO) {

        schedulerService.addHotelToScheduler(schedulerId, hotelDTO);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CommonResponse.of("스케줄러에 호텔 추가 성공", null));
    }

    @PostMapping("/{schedulerId}/information")
    public ResponseEntity<CommonResponse<Void>> addInformationToScheduler(
            @PathVariable Long schedulerId,
            @RequestBody SchedulerInformationRequestDTO informationDTO) {

        schedulerService.addInformationToScheduler(schedulerId, informationDTO);

        return ResponseEntity.ok(CommonResponse.of("스케줄러에 안내소 추가 성공", null));
    }

    @PostMapping("/{schedulerId}/landmarks")
    public ResponseEntity<CommonResponse<Void>> addLandmarkToScheduler(
            @PathVariable Long schedulerId,
            @RequestBody @Valid SchedulerLandmarkRequestDTO landmarkDTO) {

        schedulerService.addLandmarkToScheduler(schedulerId, landmarkDTO);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CommonResponse.of("스케줄러에 명소 추가 성공", null));
    }

    @PostMapping("/{schedulerId}/restaurants")
    public ResponseEntity<CommonResponse<Void>> addRestaurantToScheduler(
            @PathVariable Long schedulerId,
            @RequestBody SchedulerRestaurantRequestDTO restaurantDTO) {

        schedulerService.addRestaurantToScheduler(schedulerId, restaurantDTO);

        return ResponseEntity.ok(CommonResponse.of("스케줄러에 맛집 추가 성공", null));
    }

    @PostMapping("/{schedulerId}/seoul-goods")
    public ResponseEntity<CommonResponse<Void>> addSeoulGoodsToScheduler(
            @PathVariable Long schedulerId,
            @RequestBody SchedulerSeoulGoodsRequestDTO seoulGoodsDTO) {

        schedulerService.addSeoulGoodsToScheduler(schedulerId, seoulGoodsDTO);

        return ResponseEntity.ok(CommonResponse.of("스케줄러에 기념품판매소 추가 성공", null));
    }
}