package dnaaaaahtac.wooriforei.domain.faq.controller;

import dnaaaaahtac.wooriforei.domain.faq.dto.FaqRequestDTO;
import dnaaaaahtac.wooriforei.domain.faq.dto.FaqResponseDTO;
import dnaaaaahtac.wooriforei.domain.faq.service.FaqService;
import dnaaaaahtac.wooriforei.global.common.CommonResponse;
import dnaaaaahtac.wooriforei.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/faqs")
public class FaqController {

    private final FaqService faqService;

    @PostMapping
    public ResponseEntity<CommonResponse<FaqResponseDTO>> createFaq(
            @RequestBody @Validated FaqRequestDTO faqRequestDTO) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        Long userId = userDetails.getUserId();

        FaqResponseDTO createdFaq = faqService.createFaq(faqRequestDTO, userId);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CommonResponse.of("FAQ 생성 성공", createdFaq));
    }

    @GetMapping("/{faqId}")
    public ResponseEntity<CommonResponse<FaqResponseDTO>> getFaqById(
            @PathVariable Long faqId) {

        FaqResponseDTO faq = faqService.getFaqById(faqId);

        return ResponseEntity.ok(CommonResponse.of("FAQ 단일 조회 성공", faq));
    }

    @GetMapping
    public ResponseEntity<CommonResponse<List<FaqResponseDTO>>> getAllFaqs() {

        List<FaqResponseDTO> faqs = faqService.getAllFaqs();

        return ResponseEntity.ok()
                .body(CommonResponse.of("FAQ 전체 조회 성공", faqs));
    }

    @PutMapping("/{faqId}")
    public ResponseEntity<CommonResponse<FaqResponseDTO>> updateFaq(
            @PathVariable Long faqId,
            @RequestBody @Validated FaqRequestDTO faqRequestDTO) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        Long userId = userDetails.getUserId();

        FaqResponseDTO updatedFaq = faqService.updateFaq(faqId, faqRequestDTO, userId);

        return ResponseEntity.ok()
                .body(CommonResponse.of("FAQ 수정 성공", updatedFaq));
    }
}
