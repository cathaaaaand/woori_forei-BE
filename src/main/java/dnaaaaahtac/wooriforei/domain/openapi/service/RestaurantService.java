package dnaaaaahtac.wooriforei.domain.openapi.service;

import dnaaaaahtac.wooriforei.domain.openapi.dto.restaurant.RestaurantDetailDTO;
import dnaaaaahtac.wooriforei.domain.openapi.dto.restaurant.RestaurantResponseDTO;
import dnaaaaahtac.wooriforei.domain.openapi.entity.Restaurant;
import dnaaaaahtac.wooriforei.domain.openapi.repository.RestaurantRepository;
import dnaaaaahtac.wooriforei.global.exception.CustomException;
import dnaaaaahtac.wooriforei.global.exception.ErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;

@Service
public class RestaurantService {

    private final WebClient webClient;
    private final String authKey;
    private final RestaurantRepository restaurantRepository;

    @Autowired
    public RestaurantService(
            WebClient.Builder webClientBuilder,
            @Value("${AUTH_KEY}") String authKey,
            RestaurantRepository restaurantRepository) {

        ExchangeStrategies exchangeStrategies = ExchangeStrategies.builder()
                .codecs(clientCodecConfigurer -> clientCodecConfigurer
                        .defaultCodecs()
                        .maxInMemorySize(16 * 1024 * 1024)) // 16MB로 설정
                .build();

        this.webClient = webClientBuilder
                .baseUrl("http://openapi.seoul.go.kr:8088")
                .exchangeStrategies(exchangeStrategies)
                .build();
        this.authKey = authKey;
        this.restaurantRepository = restaurantRepository;
    }

    public Flux<RestaurantResponseDTO> retrieveRestaurantPage() {

        int totalItems = 5999;
        int pageSize = 1000;

        // 총 페이지 수 계산
        int totalPages = (totalItems + pageSize - 1) / pageSize;

        return Flux.range(1, totalPages)
                .flatMap(page -> {
                    int startIdx = (page - 1) * pageSize + 1;
                    int endIdx = startIdx + pageSize - 1;
                    if (endIdx > totalItems) {
                        endIdx = totalItems;
                    }
                    return retrieveRestaurant(startIdx, endIdx);
                });
    }

    private Mono<RestaurantResponseDTO> retrieveRestaurant(int startIdx, int endIdx) {

        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/{authKey}/{responseType}/{serviceName}/{startIdx}/{endIdx}")
                        .build(authKey, "json", "TbVwRestaurants", startIdx, endIdx))
                .header("Content-type", "application/json")
                .retrieve()
                .bodyToMono(RestaurantResponseDTO.class)
                .doOnSuccess(response -> {
                    System.out.println("Successfully retrieved data from " + startIdx + " to " + endIdx);
                    saveRestaurantDetails(response);
                })
                .doOnError(e -> System.out.println("Error retrieving data from " + startIdx + " to " + endIdx + ": " + e.getMessage()));
    }

    // 전체 조회
    public List<Restaurant> findAllRestaurant() {

        return restaurantRepository.findAll();
    }

    // 단건 조회
    public Mono<Restaurant> findRestaurantById(Long id) {

        Optional<Restaurant> restaurantOptional = restaurantRepository.findById(id);

        // 정보를 찾을 수 없는 경우 예외 발생
        if (restaurantOptional.isEmpty()) {
            return Mono.error(new CustomException(ErrorCode.NOT_FOUND_DATA));
        }

        return Mono.justOrEmpty(restaurantOptional);
    }

    @Transactional
    public void saveRestaurantDetails(RestaurantResponseDTO response) {

        response.getTbVwRestaurants().getRow().stream()
                .filter(detail -> "ko".equals(detail.getLangCodeId()))
                .forEach(this::convertAndSave);
    }

    @Transactional
    public void convertAndSave(RestaurantDetailDTO detail) {

        restaurantRepository.findByPostSn(detail.getPostSn())
                .ifPresentOrElse(existingRest -> updateExistingRestaurant(existingRest, detail),
                        () -> restaurantRepository.save(mapToEntity(new Restaurant(), detail)));
    }

    private Restaurant mapToEntity(Restaurant restaurant, RestaurantDetailDTO detail) {
        // DTO -> Entity 매핑
        restaurant.setPostSn(detail.getPostSn());
        restaurant.setLangCodeId(detail.getLangCodeId());
        restaurant.setPostSj(detail.getPostSj());
        restaurant.setAddress(detail.getAddress());
        restaurant.setNewAddress(detail.getNewAddress());
        restaurant.setCmmnTelno(detail.getCmmnTelNo());
        restaurant.setCmmnHmpgUrl(detail.getCmmnHmpgUrl());
        restaurant.setCmmnUseTime(detail.getCmmnUseTime());
        restaurant.setSubwayInfo(detail.getSubwayInfo());
        restaurant.setFdReprsntMenu(detail.getFdReprsntMenu());

        return restaurant;
    }

    private void updateExistingRestaurant(Restaurant existingRest, RestaurantDetailDTO detail) {

        boolean updated = false;

        if (notEqual(existingRest.getPostSn(), detail.getPostSn())) {
            existingRest.setPostSn(detail.getPostSn());
            updated = true;
        }

        if (notEqual(existingRest.getLangCodeId(), detail.getLangCodeId())) {
            existingRest.setLangCodeId(detail.getLangCodeId());
            updated = true;
        }

        if (notEqual(existingRest.getPostSj(), detail.getPostSj())) {
            existingRest.setPostSj(detail.getPostSj());
            updated = true;
        }

        if (notEqual(existingRest.getAddress(), detail.getAddress())) {
            existingRest.setAddress(detail.getAddress());
            updated = true;
        }

        if (notEqual(existingRest.getNewAddress(), detail.getNewAddress())) {
            existingRest.setNewAddress(detail.getNewAddress());
            updated = true;
        }

        if (notEqual(existingRest.getCmmnTelno(), detail.getCmmnTelNo())) {
            existingRest.setCmmnTelno(detail.getCmmnTelNo());
            updated = true;
        }

        if (notEqual(existingRest.getCmmnHmpgUrl(), detail.getCmmnHmpgUrl())) {
            existingRest.setCmmnHmpgUrl(detail.getCmmnHmpgUrl());
            updated = true;
        }

        if (notEqual(existingRest.getCmmnUseTime(), detail.getCmmnUseTime())) {
            existingRest.setCmmnUseTime(detail.getCmmnUseTime());
            updated = true;
        }


        if (notEqual(existingRest.getSubwayInfo(), detail.getSubwayInfo())) {
            existingRest.setSubwayInfo(detail.getSubwayInfo());
            updated = true;
        }

        if (notEqual(existingRest.getFdReprsntMenu(), detail.getFdReprsntMenu())) {
            existingRest.setFdReprsntMenu(detail.getFdReprsntMenu());
            updated = true;
        }

        if (updated) {
            restaurantRepository.save(existingRest);

        }
    }

    private boolean notEqual(String existingValue, String newValue) {

        return existingValue == null ? newValue != null : !existingValue.equals(newValue);
    }

}
