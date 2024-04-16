package dnaaaaahtac.wooriforei.domain.scheduler.controller;

import dnaaaaahtac.wooriforei.domain.scheduler.dto.SchedulerCreateRequestDTO;
import dnaaaaahtac.wooriforei.domain.scheduler.dto.SchedulerCreateResponseDTO;
import dnaaaaahtac.wooriforei.domain.scheduler.service.SchedulerService;
import dnaaaaahtac.wooriforei.global.common.CommonResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/schedulers")
@RequiredArgsConstructor
public class SchedulerController {

    private final SchedulerService schedulerService;

    @PostMapping
    public ResponseEntity<CommonResponse<SchedulerCreateResponseDTO>> createScheduler(
            @RequestBody @Valid SchedulerCreateRequestDTO schedulerCreateRequestDTO) {

        SchedulerCreateResponseDTO schedulerResponse = schedulerService.createScheduler(schedulerCreateRequestDTO);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CommonResponse.of("스케줄러 생성 성공", schedulerResponse));
    }
}