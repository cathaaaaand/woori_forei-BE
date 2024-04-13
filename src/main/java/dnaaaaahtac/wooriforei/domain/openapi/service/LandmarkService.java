package dnaaaaahtac.wooriforei.domain.openapi.service;

import dnaaaaahtac.wooriforei.domain.openapi.dto.hotel.HotelDetailDTO;
import dnaaaaahtac.wooriforei.domain.openapi.dto.landmark.LandmarkDetailDTO;
import dnaaaaahtac.wooriforei.domain.openapi.dto.landmark.LandmarkResponseDTO;
import dnaaaaahtac.wooriforei.domain.openapi.entity.Hotel;
import dnaaaaahtac.wooriforei.domain.openapi.entity.Landmark;
import dnaaaaahtac.wooriforei.domain.openapi.repository.LandmarkRepository;
import dnaaaaahtac.wooriforei.global.exception.CustomException;
import dnaaaaahtac.wooriforei.global.exception.ErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;

@Service
public class LandmarkService {

    private final WebClient webClient;
    private final String authKey;
    private final LandmarkRepository landmarkRepository;

    @Autowired
    public LandmarkService(
            WebClient.Builder webClientBuilder,
            @Value("${AUTH_KEY}") String authKey,
            LandmarkRepository landmarkRepository) {

        this.webClient = webClientBuilder.baseUrl("http://openapi.seoul.go.kr:8088").build();
        this.authKey = authKey;
        this.landmarkRepository = landmarkRepository;
    }

    public Mono<LandmarkResponseDTO> retrieveHotel() {

        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/{authKey}/{responseType}/{serviceName}/{startIdx}/{endIdx}")
                        .build(authKey, "json", "SebcHotelListKor", 1, 999))
                .header("Content-type", "application/json")
                .retrieve()
                .bodyToMono(LandmarkResponseDTO.class)
                .doOnSuccess(response -> {
                    System.out.println("Successfully retrieved data");
                    saveLandmarkDetails(response);
                })
                .doOnError(e -> System.out.println("Error: " + e.getMessage()));
    }

    // 전체 조회
    public List<Landmark> findAllLandmarks() {

        return landmarkRepository.findAll();
    }

    // 단건 조회
    public Mono<Landmark> findLandmarkById(Long id) {

        Optional<Landmark> LandmarkOptional = landmarkRepository.findById(id);

        // 정보를 찾을 수 없는 경우 예외 발생
        if (LandmarkOptional.isEmpty()) {
            return Mono.error(new CustomException(ErrorCode.NOT_FOUND_DATA));
        }

        return Mono.justOrEmpty(LandmarkOptional);
    }

    @Transactional
    public void saveLandmarkDetails(LandmarkResponseDTO wrapper) {

        wrapper.getTbVwAttractions().getRow().forEach(this::convertAndSave);
    }

    @Transactional
    public void convertAndSave(LandmarkDetailDTO detail) {

        landmarkRepository.findByPostSn(detail.getPostSn())
                .ifPresentOrElse(existingLandmark -> updateExistingLandmark(existingLandmark, detail),
                        () -> landmarkRepository.save(mapToEntity(new Landmark(), detail)));
    }

    private Landmark mapToEntity(Landmark landmark, LandmarkDetailDTO detail) {
        // DTO -> Entity 매핑
        landmark.setPostSn(detail.getPostSn());
        landmark.setLangCodeId(detail.getLangCodeId());
        landmark.setPostSj(detail.getPostSj());
        landmark.setAddress(detail.getAddress());
        landmark.setNewAddress(detail.getNewAddress());
        landmark.setCmmnTelno(detail.getCmmnTelno());
        landmark.setCmmnHmpgUrl(detail.getCmmnHmpgUrl());
        landmark.setCmmnUseTime(detail.getCmmnUseTime());
        landmark.setCmmnBsnde(detail.getCmmnBsnde());
        landmark.setCmmnRstde(detail.getCmmnRstde());
        landmark.setSubwayInfo(detail.getSubwayInfo());
        landmark.setBfDesc(detail.getBfDesc());

        return landmark;
    }

    private void updateExistingLandmark(Landmark existingLandmark, LandmarkDetailDTO detail) {

        boolean updated = false;

        if (notEqual(existingLandmark.getPostSn(), detail.getPostSn())) {
            existingLandmark.setPostSn(detail.getPostSn());
            updated = true;
        }

        if (notEqual(existingLandmark.getLangCodeId(), detail.getLangCodeId())) {
            existingLandmark.setLangCodeId(detail.getLangCodeId());
            updated = true;
        }

        if (notEqual(existingLandmark.getPostSj(), detail.getPostSj())) {
            existingLandmark.setPostSj(detail.getPostSj());
            updated = true;
        }

        if (notEqual(existingLandmark.getAddress(), detail.getAddress())) {
            existingLandmark.setAddress(detail.getAddress());
            updated = true;
        }

        if (notEqual(existingLandmark.getNewAddress(), detail.getNewAddress())) {
            existingLandmark.setNewAddress(detail.getNewAddress());
            updated = true;
        }

        if (notEqual(existingLandmark.getCmmnTelno(), detail.getCmmnTelno())) {
            existingLandmark.setCmmnTelno(detail.getCmmnTelno());
            updated = true;
        }

        if (notEqual(existingLandmark.getCmmnHmpgUrl(), detail.getCmmnHmpgUrl())) {
            existingLandmark.setCmmnHmpgUrl(detail.getCmmnHmpgUrl());
            updated = true;
        }

        if (notEqual(existingLandmark.getCmmnUseTime(), detail.getCmmnUseTime())) {
            existingLandmark.setCmmnUseTime(detail.getCmmnUseTime());
            updated = true;
        }

        if (notEqual(existingLandmark.getCmmnBsnde(), detail.getCmmnBsnde())) {
            existingLandmark.setCmmnBsnde(detail.getCmmnBsnde());
            updated = true;
        }

        if (notEqual(existingLandmark.getCmmnRstde(), detail.getCmmnRstde())) {
            existingLandmark.setCmmnRstde(detail.getCmmnRstde());
            updated = true;
        }

        if (notEqual(existingLandmark.getSubwayInfo(), detail.getSubwayInfo())) {
            existingLandmark.setSubwayInfo(detail.getSubwayInfo());
            updated = true;
        }

        if (notEqual(existingLandmark.getBfDesc(), detail.getBfDesc())) {
            existingLandmark.setBfDesc(detail.getBfDesc());
            updated = true;
        }

        if (updated) {
            landmarkRepository.save(existingLandmark);

        }
    }

    private boolean notEqual(String existingValue, String newValue) {

        return existingValue == null ? newValue != null : !existingValue.equals(newValue);
    }

}
