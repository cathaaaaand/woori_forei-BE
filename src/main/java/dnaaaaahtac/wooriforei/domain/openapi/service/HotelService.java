package dnaaaaahtac.wooriforei.domain.openapi.service;

import dnaaaaahtac.wooriforei.domain.openapi.dto.hotel.HotelDetailDTO;
import dnaaaaahtac.wooriforei.domain.openapi.dto.hotel.HotelResponseDTO;
import dnaaaaahtac.wooriforei.domain.openapi.entity.Hotel;
import dnaaaaahtac.wooriforei.domain.openapi.repository.HotelRepository;
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
public class HotelService {

    private final WebClient webClient;
    private final String authKey;
    private final HotelRepository hotelRepository;

    @Autowired
    public HotelService(
            WebClient.Builder webClientBuilder,
            @Value("${AUTH_KEY}") String authKey,
            HotelRepository hotelRepository) {

        this.webClient = webClientBuilder.baseUrl("http://openapi.seoul.go.kr:8088").build();
        this.authKey = authKey;
        this.hotelRepository = hotelRepository;
    }

    public Mono<HotelResponseDTO> retrieveHotel() {

        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/{authKey}/{responseType}/{serviceName}/{startIdx}/{endIdx}")
                        .build(authKey, "json", "SebcHotelListKor", 1, 999))
                .header("Content-type", "application/json")
                .retrieve()
                .bodyToMono(HotelResponseDTO.class)
                .doOnSuccess(response -> {
                    System.out.println("Successfully retrieved data");
                    saveHotelDetails(response);
                })
                .doOnError(e -> System.out.println("Error: " + e.getMessage()));
    }

    // 전체 조회
    public List<Hotel> findAllHotels() {

        return hotelRepository.findAll();
    }

    // 단건 조회
    public Mono<Hotel> findHotelById(Long id) {

        Optional<Hotel> HotelOptional = hotelRepository.findById(id);

        // 정보를 찾을 수 없는 경우 예외 발생
        if (HotelOptional.isEmpty()) {
            return Mono.error(new CustomException(ErrorCode.NOT_FOUND_DATA));
        }

        return Mono.justOrEmpty(HotelOptional);
    }

    @Transactional
    public void saveHotelDetails(HotelResponseDTO wrapper) {

        wrapper.getSebcHotelListKor().getRow().forEach(this::convertAndSave);
    }

    @Transactional
    public void convertAndSave(HotelDetailDTO detail) {

        hotelRepository.findByMainKey(detail.getMainKey())
                .ifPresentOrElse(existingHotel -> updateExistingHotel(existingHotel, detail),
                        () -> hotelRepository.save(mapToEntity(new Hotel(), detail)));
    }

    private Hotel mapToEntity(Hotel hotel, HotelDetailDTO detail) {
        // DTO -> Entity 매핑
        hotel.setMainKey(detail.getMainKey());
        hotel.setCate3Name(detail.getCate3Name());
        hotel.setNameKor(detail.getNameKor());
        hotel.setHKorCity(detail.getHKorCity());
        hotel.setHKorGu(detail.getHKorGu());
        hotel.setHKorDong(detail.getHKorDong());

        return hotel;
    }

    private void updateExistingHotel(Hotel existingHotel, HotelDetailDTO detail) {

        boolean updated = false;

        if (notEqual(existingHotel.getMainKey(), detail.getMainKey())) {
            existingHotel.setMainKey(detail.getMainKey());
            updated = true;
        }

        if (notEqual(existingHotel.getCate3Name(), detail.getCate3Name())) {
            existingHotel.setCate3Name(detail.getCate3Name());
            updated = true;
        }

        if (notEqual(existingHotel.getNameKor(), detail.getNameKor())) {
            existingHotel.setNameKor(detail.getNameKor());
            updated = true;
        }

        if (notEqual(existingHotel.getHKorCity(), detail.getHKorCity())) {
            existingHotel.setHKorCity(detail.getHKorCity());
            updated = true;
        }

        if (notEqual(existingHotel.getHKorGu(), detail.getHKorGu())) {
            existingHotel.setHKorGu(detail.getHKorGu());
            updated = true;
        }

        if (notEqual(existingHotel.getHKorDong(), detail.getHKorDong())) {
            existingHotel.setHKorDong(detail.getHKorDong());
            updated = true;
        }

        if (updated) {
            hotelRepository.save(existingHotel);

        }
    }

    private boolean notEqual(String existingValue, String newValue) {

        return existingValue == null ? newValue != null : !existingValue.equals(newValue);
    }

}
