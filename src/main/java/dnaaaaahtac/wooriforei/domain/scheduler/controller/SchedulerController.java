package dnaaaaahtac.wooriforei.domain.scheduler.controller;

import dnaaaaahtac.wooriforei.domain.scheduler.dto.SchedulerRequestDTO;
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
    public ResponseEntity<CommonResponse<String>> deleteScheduler(
            @PathVariable Long schedulerId) {

        schedulerService.deleteScheduler(schedulerId);

        return ResponseEntity.ok(CommonResponse.of("스케줄러 삭제 성공", null));
    }
}