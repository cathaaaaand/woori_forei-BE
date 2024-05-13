package dnaaaaahtac.wooriforei.domain.scheduler.controller;

import dnaaaaahtac.wooriforei.domain.scheduler.dto.*;
import dnaaaaahtac.wooriforei.domain.scheduler.service.SchedulerService;
import dnaaaaahtac.wooriforei.global.common.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

@Controller
@RequiredArgsConstructor
public class WebSocketController {

    private final SchedulerService schedulerService;

    @MessageMapping("/scheduler/addMember")
    @SendTo("/topic/schedulerResponse")
    public CommonResponse<SchedulerResponseDTO> addMemberToScheduler(
            Long schedulerId, String userEmail) {

        SchedulerResponseDTO updatedScheduler = schedulerService.addMemberToScheduler(schedulerId, userEmail);

        return CommonResponse.of("스케줄러에 사용자 추가 성공", updatedScheduler);
    }

    @MessageMapping("/scheduler/create")
    @SendTo("/topic/schedulerResponse")
    public CommonResponse<SchedulerResponseDTO> createScheduler(
            SchedulerRequestDTO schedulerRequestDTO) {

        SchedulerResponseDTO schedulerResponse = schedulerService.createScheduler(schedulerRequestDTO);

        return CommonResponse.of("스케줄러 생성 성공", schedulerResponse);
    }

    @MessageMapping("/scheduler/update")
    @SendTo("/topic/schedulerResponse")
    public CommonResponse<SchedulerResponseDTO> updateScheduler(
            SchedulerRequestDTO schedulerRequestDTO) {

        SchedulerResponseDTO updatedScheduler
                = schedulerService.updateScheduler(schedulerRequestDTO.getSchedulerId(), schedulerRequestDTO);

        return CommonResponse.of("스케줄러 수정 성공", updatedScheduler);
    }

    @MessageMapping("/scheduler/delete")
    @SendTo("/topic/schedulerResponse")
    public CommonResponse<Void> deleteScheduler(
            Long schedulerId) {

        schedulerService.deleteScheduler(schedulerId);

        return CommonResponse.of("스케줄러 삭제 성공", null);
    }

    @MessageMapping("/scheduler/get")
    @SendTo("/topic/schedulerResponse")
    public CommonResponse<SchedulerResponseDTO> getScheduler(
            Long schedulerId) {

        SchedulerResponseDTO scheduler = schedulerService.getSchedulerById(schedulerId);

        return CommonResponse.of("스케줄러 단일 조회 성공", scheduler);
    }

    @MessageMapping("/scheduler/addActivity")
    @SendTo("/topic/schedulerResponse")
    public CommonResponse<Void> addActivityToScheduler(
            SchedulerActivityRequestDTO activityRequestDTO) {

        schedulerService.addActivityToScheduler(activityRequestDTO.getActivityId(), activityRequestDTO);

        return CommonResponse.of("스케줄러에 문화체험 추가 성공", null);
    }

    @MessageMapping("/scheduler/addHotel")
    @SendTo("/topic/schedulerResponse")
    public CommonResponse<Void> addHotelToScheduler(
            SchedulerHotelRequestDTO hotelRequestDTO) {

        schedulerService.addHotelToScheduler(hotelRequestDTO.getHotelId(), hotelRequestDTO);

        return CommonResponse.of("스케줄러에 호텔 추가 성공", null);
    }

    @MessageMapping("/scheduler/addInformation")
    @SendTo("/topic/schedulerResponse")
    public CommonResponse<Void> addInformationToScheduler(
            SchedulerInformationRequestDTO informationRequestDTO) {

        schedulerService.addInformationToScheduler(informationRequestDTO.getInformationId(), informationRequestDTO);

        return CommonResponse.of("스케줄러에 안내소 추가 성공", null);
    }

    @MessageMapping("/scheduler/addLandmark")
    @SendTo("/topic/schedulerResponse")
    public CommonResponse<Void> addLandmarkToScheduler(
            SchedulerLandmarkRequestDTO landmarkRequestDTO) {

        schedulerService.addLandmarkToScheduler(landmarkRequestDTO.getLandmarkId(), landmarkRequestDTO);

        return CommonResponse.of("스케줄러에 명소 추가 성공", null);
    }

    @MessageMapping("/scheduler/addRestaurant")
    @SendTo("/topic/schedulerResponse")
    public CommonResponse<Void> addRestaurantToScheduler(
            SchedulerRestaurantRequestDTO restaurantRequestDTO) {

        schedulerService.addRestaurantToScheduler(restaurantRequestDTO.getRestaurantId(), restaurantRequestDTO);

        return CommonResponse.of("스케줄러에 맛집 추가 성공", null);
    }

    @MessageMapping("/scheduler/addSeoulGoods")
    @SendTo("/topic/schedulerResponse")
    public CommonResponse<Void> addSeoulGoodsToScheduler(
            SchedulerSeoulGoodsRequestDTO seoulGoodsRequestDTO) {

        schedulerService.addSeoulGoodsToScheduler(seoulGoodsRequestDTO.getGoodsId(), seoulGoodsRequestDTO);

        return CommonResponse.of("스케줄러에 기념품판매소 추가 성공", null);
    }
}
