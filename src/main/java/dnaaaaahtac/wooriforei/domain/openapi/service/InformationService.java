package dnaaaaahtac.wooriforei.domain.openapi.service;

import dnaaaaahtac.wooriforei.domain.openapi.dto.information.InformationDetailDTO;
import dnaaaaahtac.wooriforei.domain.openapi.dto.information.InformationResponseDTO;
import dnaaaaahtac.wooriforei.domain.openapi.entity.Information;
import dnaaaaahtac.wooriforei.domain.openapi.repository.InformationRepository;
import dnaaaaahtac.wooriforei.global.exception.CustomException;
import dnaaaaahtac.wooriforei.global.exception.ErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;

@Service
public class InformationService {

    private final WebClient webClient;
    private final String authKey;
    private final InformationRepository informationRepository;

    @Autowired
    public InformationService(
            WebClient.Builder webClientBuilder,
            @Value("${AUTH_KEY}") String authKey,
            InformationRepository informationRepository) {

        this.webClient = webClientBuilder.baseUrl("http://openapi.seoul.go.kr:8088").build();
        this.authKey = authKey;
        this.informationRepository = informationRepository;
    }

    public Mono<InformationResponseDTO> retrieveInformation() {

        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/{authKey}/{responseType}/{serviceName}/{startIdx}/{endIdx}")
                        .build(authKey, "json", "TbTourInformation", 1, 999))
                .header("Content-type", "application/json")
                .retrieve()
                .bodyToMono(InformationResponseDTO.class)
                .doOnSuccess(response -> {
                    System.out.println("Successfully retrieved data");
                    saveInformationDetails(response);
                })
                .doOnError(e -> System.out.println("Error: " + e.getMessage()));
    }


    // 전체 조회
    public List<Information> findAllInformations() {

        return informationRepository.findAll();
    }

    // 단건 조회
    public Mono<Information> findInformationById(Long id) {

        Optional<Information> informationOptional = informationRepository.findById(id);

        // 정보를 찾을 수 없는 경우 예외 발생
        if (informationOptional.isEmpty()) {
            return Mono.error(new CustomException(ErrorCode.NOT_FOUND_DATA));
        }

        return Mono.justOrEmpty(informationOptional);
    }

    private void saveInformationDetails(InformationResponseDTO wrapper) {

        wrapper.getTbTourInformation().getRow().forEach(this::convertAndSave);
    }

    private void convertAndSave(InformationDetailDTO detail) {

        informationRepository.findByTrsmicnmAndSigngunm(detail.getTrsmicnm(), detail.getSigngunm())
                .ifPresentOrElse(existingInfo -> updateExistingInformation(existingInfo, detail),
                        () -> informationRepository.save(mapToEntity(new Information(), detail)));
    }

    private Information mapToEntity(Information information, InformationDetailDTO detail) {
        // DTO -> Entity 매핑
        information.setTrsmicnm(detail.getTrsmicnm());
        information.setSigngunm(detail.getSigngunm());
        information.setTrsmicintrcn(detail.getTrsmicintrcn());
        information.setAdisvcinfo(detail.getAdisvcinfo());
        information.setRstde(detail.getRstde());
        information.setSummeroperopenhhmm(detail.getSummeroperopenhhmm());
        information.setSummeroperclosehhmm(detail.getSummeroperclosehhmm());
        information.setWinteroperopenhhmm(detail.getWinteroperopenhhmm());
        information.setWinteroperclosehhmm(detail.getWinteroperclosehhmm());
        information.setEngguidanceyn(detail.getEngguidanceyn());
        information.setJpguidanceyn(detail.getJpguidanceyn());
        information.setChguidanceyn(detail.getChguidanceyn());
        information.setGuidancephonenumber(detail.getGuidancephonenumber());
        information.setRdnmadr(detail.getRdnmadr());
        information.setLnmadr(detail.getLnmadr());
        information.setHomepageurl(detail.getHomepageurl());
        information.setLatitude(detail.getLatitude());
        information.setLongitude(detail.getLongitude());

        return information;
    }

    private void updateExistingInformation(Information existingInfo, InformationDetailDTO detail) {

        boolean updated = false;

        if (notEqual(existingInfo.getTrsmicnm(), detail.getTrsmicnm())) {
            existingInfo.setTrsmicnm(detail.getTrsmicnm());
            updated = true;
        }

        if (notEqual(existingInfo.getSigngunm(), detail.getSigngunm())) {
            existingInfo.setSigngunm(detail.getSigngunm());
            updated = true;
        }

        if (notEqual(existingInfo.getTrsmicintrcn(), detail.getTrsmicintrcn())) {
            existingInfo.setTrsmicintrcn(detail.getTrsmicintrcn());
            updated = true;
        }

        if (notEqual(existingInfo.getAdisvcinfo(), detail.getAdisvcinfo())) {
            existingInfo.setAdisvcinfo(detail.getAdisvcinfo());
            updated = true;
        }

        if (notEqual(existingInfo.getRstde(), detail.getRstde())) {
            existingInfo.setRstde(detail.getRstde());
            updated = true;
        }

        if (notEqual(existingInfo.getSummeroperopenhhmm(), detail.getSummeroperopenhhmm())) {
            existingInfo.setSummeroperopenhhmm(detail.getSummeroperopenhhmm());
            updated = true;
        }

        if (notEqual(existingInfo.getSummeroperclosehhmm(), detail.getSummeroperclosehhmm())) {
            existingInfo.setSummeroperclosehhmm(detail.getSummeroperclosehhmm());
            updated = true;
        }

        if (notEqual(existingInfo.getWinteroperopenhhmm(), detail.getWinteroperopenhhmm())) {
            existingInfo.setWinteroperopenhhmm(detail.getWinteroperopenhhmm());
            updated = true;
        }

        if (notEqual(existingInfo.getWinteroperclosehhmm(), detail.getWinteroperclosehhmm())) {
            existingInfo.setWinteroperclosehhmm(detail.getWinteroperclosehhmm());
            updated = true;
        }

        if (notEqual(existingInfo.getEngguidanceyn(), detail.getEngguidanceyn())) {
            existingInfo.setEngguidanceyn(detail.getEngguidanceyn());
            updated = true;
        }

        if (notEqual(existingInfo.getJpguidanceyn(), detail.getJpguidanceyn())) {
            existingInfo.setJpguidanceyn(detail.getJpguidanceyn());
            updated = true;
        }

        if (notEqual(existingInfo.getChguidanceyn(), detail.getChguidanceyn())) {
            existingInfo.setChguidanceyn(detail.getChguidanceyn());
            updated = true;
        }

        if (notEqual(existingInfo.getGuidancephonenumber(), detail.getGuidancephonenumber())) {
            existingInfo.setGuidancephonenumber(detail.getGuidancephonenumber());
            updated = true;
        }

        if (notEqual(existingInfo.getRdnmadr(), detail.getRdnmadr())) {
            existingInfo.setRdnmadr(detail.getRdnmadr());
            updated = true;
        }

        if (notEqual(existingInfo.getLnmadr(), detail.getLnmadr())) {
            existingInfo.setLnmadr(detail.getLnmadr());
            updated = true;
        }

        if (notEqual(existingInfo.getHomepageurl(), detail.getHomepageurl())) {
            existingInfo.setHomepageurl(detail.getHomepageurl());
            updated = true;
        }

        if (notEqual(existingInfo.getLatitude(), detail.getLatitude())) {
            existingInfo.setLatitude(detail.getLatitude());
            updated = true;
        }

        if (notEqual(existingInfo.getLongitude(), detail.getLongitude())) {
            existingInfo.setLongitude(detail.getLongitude());
            updated = true;
        }

        if (updated) {
            informationRepository.save(existingInfo);

        }
    }

    private boolean notEqual(String existingValue, String newValue) {

        return existingValue == null ? newValue != null : !existingValue.equals(newValue);
    }

}