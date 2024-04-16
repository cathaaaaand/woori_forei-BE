package dnaaaaahtac.wooriforei.domain.scheduler.controller;

import dnaaaaahtac.wooriforei.domain.scheduler.dto.SchedulerCreateRequestDTO;
import dnaaaaahtac.wooriforei.domain.scheduler.dto.SchedulerResponseDTO;
import dnaaaaahtac.wooriforei.domain.scheduler.service.SchedulerService;
import dnaaaaahtac.wooriforei.global.common.CommonResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/schedulers")
@RequiredArgsConstructor
public class SchedulerController {

    private final SchedulerService schedulerService;

    @PostMapping
    public ResponseEntity<CommonResponse<SchedulerResponseDTO>> createScheduler(
            @RequestBody @Valid SchedulerCreateRequestDTO schedulerCreateRequestDTO) {

        SchedulerResponseDTO schedulerResponse = schedulerService.createScheduler(schedulerCreateRequestDTO);

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
}