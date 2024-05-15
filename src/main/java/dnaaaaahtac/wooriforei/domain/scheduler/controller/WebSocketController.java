package dnaaaaahtac.wooriforei.domain.scheduler.controller;

import dnaaaaahtac.wooriforei.domain.scheduler.dto.*;
import dnaaaaahtac.wooriforei.domain.scheduler.service.SchedulerService;
import dnaaaaahtac.wooriforei.global.common.CommonResponse;
import dnaaaaahtac.wooriforei.global.security.UserDetailsImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequiredArgsConstructor
public class WebSocketController {

    private final SchedulerService schedulerService;

    @MessageMapping("/scheduler/addMember")
    @SendTo("/topic/schedulerResponse")
    public CommonResponse<SchedulerResponseDTO> addMemberToScheduler(
            @AuthenticationPrincipal UserDetailsImpl userDetails, Long schedulerId, String userEmail) {

        SchedulerResponseDTO updatedScheduler = schedulerService.addMemberToScheduler(userDetails, schedulerId, userEmail);

        return CommonResponse.of("스케줄러에 사용자 추가 성공", updatedScheduler);
    }

    @PostMapping("/create-scheduler")
    public ResponseEntity<CommonResponse<SchedulerResponseDTO>> createScheduler(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestBody @Valid SchedulerRequestDTO schedulerRequestDTO) {

        SchedulerResponseDTO schedulerResponse = schedulerService.createScheduler(userDetails, schedulerRequestDTO);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CommonResponse.of("스케줄러 생성 성공", schedulerResponse));
    }

    @MessageMapping("/scheduler/update")
    @SendTo("/topic/schedulerResponse")
    public CommonResponse<SchedulerResponseDTO> updateScheduler(
            @AuthenticationPrincipal UserDetailsImpl userDetails, SchedulerRequestDTO schedulerRequestDTO) {

        SchedulerResponseDTO updatedScheduler
                = schedulerService.updateScheduler(userDetails, schedulerRequestDTO.getSchedulerId(), schedulerRequestDTO);

        return CommonResponse.of("스케줄러 수정 성공", updatedScheduler);
    }

    @MessageMapping("/scheduler/delete")
    @SendTo("/topic/schedulerResponse")
    public CommonResponse<Void> deleteScheduler(
            @AuthenticationPrincipal UserDetailsImpl userDetails, Long schedulerId) {

        schedulerService.deleteScheduler(userDetails, schedulerId);

        return CommonResponse.of("스케줄러 삭제 성공", null);
    }

    @MessageMapping("/scheduler/get")
    @SendTo("/topic/schedulerResponse")
    public CommonResponse<SchedulerResponseDTO> getScheduler(
            @AuthenticationPrincipal UserDetailsImpl userDetails, Long schedulerId) {

        SchedulerResponseDTO scheduler = schedulerService.getSchedulerById(userDetails, schedulerId);

        return CommonResponse.of("스케줄러 단일 조회 성공", scheduler);
    }

    @MessageMapping("/scheduler/addActivity")
    @SendTo("/topic/schedulerResponse")
    public CommonResponse<Void> addActivityToScheduler(
            @AuthenticationPrincipal UserDetailsImpl userDetails, SchedulerActivityRequestDTO activityRequestDTO) {

        schedulerService.addActivityToScheduler(userDetails, activityRequestDTO.getActivityId(), activityRequestDTO);

        return CommonResponse.of("스케줄러에 문화체험 추가 성공", null);
    }

    @MessageMapping("/scheduler/addHotel")
    @SendTo("/topic/schedulerResponse")
    public CommonResponse<Void> addHotelToScheduler(
            @AuthenticationPrincipal UserDetailsImpl userDetails, SchedulerHotelRequestDTO hotelRequestDTO) {

        schedulerService.addHotelToScheduler(userDetails, hotelRequestDTO.getHotelId(), hotelRequestDTO);

        return CommonResponse.of("스케줄러에 호텔 추가 성공", null);
    }

    @MessageMapping("/scheduler/addInformation")
    @SendTo("/topic/schedulerResponse")
    public CommonResponse<Void> addInformationToScheduler(
            @AuthenticationPrincipal UserDetailsImpl userDetails, SchedulerInformationRequestDTO informationRequestDTO) {

        schedulerService.addInformationToScheduler(userDetails, informationRequestDTO.getInformationId(), informationRequestDTO);

        return CommonResponse.of("스케줄러에 안내소 추가 성공", null);
    }

    @MessageMapping("/scheduler/addLandmark")
    @SendTo("/topic/schedulerResponse")
    public CommonResponse<Void> addLandmarkToScheduler(
            @AuthenticationPrincipal UserDetailsImpl userDetails, SchedulerLandmarkRequestDTO landmarkRequestDTO) {

        schedulerService.addLandmarkToScheduler(userDetails, landmarkRequestDTO.getLandmarkId(), landmarkRequestDTO);

        return CommonResponse.of("스케줄러에 명소 추가 성공", null);
    }

    @MessageMapping("/scheduler/addRestaurant")
    @SendTo("/topic/schedulerResponse")
    public CommonResponse<Void> addRestaurantToScheduler(
            @AuthenticationPrincipal UserDetailsImpl userDetails, SchedulerRestaurantRequestDTO restaurantRequestDTO) {

        schedulerService.addRestaurantToScheduler(userDetails, restaurantRequestDTO.getRestaurantId(), restaurantRequestDTO);

        return CommonResponse.of("스케줄러에 맛집 추가 성공", null);
    }

    @MessageMapping("/scheduler/addSeoulGoods")
    @SendTo("/topic/schedulerResponse")
    public CommonResponse<Void> addSeoulGoodsToScheduler(
            @AuthenticationPrincipal UserDetailsImpl userDetails, SchedulerSeoulGoodsRequestDTO seoulGoodsRequestDTO) {

        schedulerService.addSeoulGoodsToScheduler(userDetails, seoulGoodsRequestDTO.getGoodsId(), seoulGoodsRequestDTO);

        return CommonResponse.of("스케줄러에 기념품판매소 추가 성공", null);
    }
}