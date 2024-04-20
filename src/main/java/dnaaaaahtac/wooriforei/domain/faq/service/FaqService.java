package dnaaaaahtac.wooriforei.domain.faq.service;

import dnaaaaahtac.wooriforei.domain.faq.dto.FaqRequestDTO;
import dnaaaaahtac.wooriforei.domain.faq.dto.FaqResponseDTO;
import dnaaaaahtac.wooriforei.domain.faq.entity.Faq;
import dnaaaaahtac.wooriforei.domain.faq.repository.FaqRepository;
import dnaaaaahtac.wooriforei.domain.user.entity.User;
import dnaaaaahtac.wooriforei.domain.user.repository.UserRepository;
import dnaaaaahtac.wooriforei.global.exception.CustomException;
import dnaaaaahtac.wooriforei.global.exception.ErrorCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FaqService {

    private final FaqRepository faqRepository;

    private final UserRepository userRepository;

    @Transactional
    public FaqResponseDTO createFaq(FaqRequestDTO faqRequestDTO, Long userId) throws CustomException {

        User user = userRepository.findById(userId).orElseThrow(() ->
                new CustomException(ErrorCode.NOT_FOUND_USER_EXCEPTION));

        if (!user.isAdmin()) {
            throw new CustomException(ErrorCode.UNAUTHORIZED_USER_ACCESS);
        }

        Faq faq = new Faq();
        faq.setFaqTitle(faqRequestDTO.getFaqTitle());
        faq.setFaqContent(faqRequestDTO.getFaqContent());
        faq.setUserId(userId);
        faq = faqRepository.save(faq);

        return convertToResponseDTO(faq);
    }

    public FaqResponseDTO getFaqById(Long faqId) {

        Faq faq = faqRepository.findById(faqId).orElseThrow(() ->
                new CustomException(ErrorCode.NOT_FOUND_FAQ));

        return convertToResponseDTO(faq);
    }

    public List<FaqResponseDTO> getAllFaqs() {

        return faqRepository.findAll().stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public FaqResponseDTO updateFaq(Long faqId, FaqRequestDTO faqRequestDTO, Long userId) {

        User user = userRepository.findById(userId).orElseThrow(() ->
                new CustomException(ErrorCode.NOT_FOUND_USER_EXCEPTION));

        if (!user.isAdmin()) {
            throw new CustomException(ErrorCode.UNAUTHORIZED_USER_ACCESS);
        }

        Faq faq = faqRepository.findById(faqId).orElseThrow(() ->
                new CustomException(ErrorCode.NOT_FOUND_FAQ));

        faq.setFaqTitle(faqRequestDTO.getFaqTitle());
        faq.setFaqContent(faqRequestDTO.getFaqContent());
        faq.setUserId(userId);
        faq = faqRepository.save(faq);

        return convertToResponseDTO(faq);
    }

    private FaqResponseDTO convertToResponseDTO(Faq faq) {

        FaqResponseDTO dto = new FaqResponseDTO();
        dto.setFaqId(faq.getId());
        dto.setFaqTitle(faq.getFaqTitle());
        dto.setFaqContent(faq.getFaqContent());
        dto.setUserId(faq.getUserId());
        dto.setCreatedAt(faq.getCreatedAt());
        dto.setModifiedAt(faq.getModifiedAt());

        return dto;
    }
}
