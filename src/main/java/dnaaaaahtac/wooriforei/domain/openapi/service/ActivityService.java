package dnaaaaahtac.wooriforei.domain.openapi.service;

import dnaaaaahtac.wooriforei.domain.openapi.dto.activity.ActivityDetailDto;
import dnaaaaahtac.wooriforei.domain.openapi.dto.activity.ActivityResponseDto;
import dnaaaaahtac.wooriforei.domain.openapi.dto.information.InformationResponseDto;
import dnaaaaahtac.wooriforei.domain.openapi.entity.Activity;
import dnaaaaahtac.wooriforei.domain.openapi.repository.ActivityRepository;
import dnaaaaahtac.wooriforei.global.exception.CustomException;
import dnaaaaahtac.wooriforei.global.exception.ErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;

@Service
public class ActivityService {

    private final WebClient webClient;
    private final String authKey;
    private final ActivityRepository activityRepository;

    @Autowired
    public ActivityService(
            WebClient.Builder webClientBuilder,
            @Value("${AUTH_KEY}") String authKey,
            ActivityRepository activityRepository) {

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
        this.activityRepository = activityRepository;
    }

    public Mono<ActivityResponseDto> retrieveActivity() {

        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/{authKey}/{responseType}/{serviceName}/{startIdx}/{endIdx}")
                        .build(authKey, "json", "ListPublicReservationCulture", 1, 999))
                .header("Content-type", "application/json")
                .retrieve()
                .bodyToMono(ActivityResponseDto.class)
                .doOnSuccess(response -> {
                    System.out.println("Successfully retrieved data");
                    saveActivityDetails(response);
                })
                .doOnError(e -> System.out.println("Error: " + e.getMessage()));
    }

    // 전체 조회
    public List<Activity> findAllActivity() {

        return activityRepository.findAll();
    }

    // 단건 조회
    public Mono<Activity> findActivityById(Long id) {

        Optional<Activity> activityOptional = activityRepository.findById(id);

        // 정보를 찾을 수 없는 경우 예외 발생
        if (activityOptional.isEmpty()) {
            return Mono.error(new CustomException(ErrorCode.NOT_FOUND_DATA));
        }

        return Mono.justOrEmpty(activityOptional);
    }

    private void saveActivityDetails(ActivityResponseDto wrapper) {

        wrapper.getListPublicReservationCulture().getRow().forEach(this::convertAndSave);
    }

    private void convertAndSave(ActivityDetailDto detail) {

        activityRepository.findBySvcid(detail.getSvcid())
                .ifPresentOrElse(existingAct -> updateExistingActivity(existingAct, detail),
                        () -> activityRepository.save(mapToEntity(new Activity(), detail)));
    }

    private Activity mapToEntity(Activity activity, ActivityDetailDto detail) {
        // DTO -> Entity 매핑
        activity.setSvcid(detail.getSvcid());
        activity.setMinclassnm(detail.getMinclassnm());
        activity.setSvcstatnm(detail.getSvcstatnm());
        activity.setSvcnm(detail.getSvcnm());
        activity.setPayatnm(detail.getPayatnm());
        activity.setPlacenm(detail.getPlacenm());
        activity.setUsetgtinfo(detail.getUsetgtinfo());
        activity.setSvcurl(detail.getSvcurl());
        activity.setX(detail.getX());
        activity.setY(detail.getY());
        activity.setSvcopnbgndt(detail.getSvcopnbgndt());
        activity.setSvcopnenddt(detail.getSvcopnenddt());
        activity.setRcptbgndt(detail.getRcptbgndt());
        activity.setRcptenddt(detail.getRcptenddt());
        activity.setImgurl(detail.getImgurl());
        activity.setVmin(detail.getVmin());
        activity.setVmax(detail.getVmax());
        activity.setRevstddaynm(detail.getRevstddaynm());
        activity.setRevstdday(detail.getRevstdday());

        return activity;
    }

    private void updateExistingActivity(Activity existingAct, ActivityDetailDto detail) {
        boolean updated = false;

        if (notEqual(existingAct.getSvcid(), detail.getSvcid())) {
            existingAct.setSvcid(detail.getSvcid());
            updated = true;
        }

        if (notEqual(existingAct.getMinclassnm(), detail.getMinclassnm())) {
            existingAct.setMinclassnm(detail.getMinclassnm());
            updated = true;
        }

        if (notEqual(existingAct.getSvcstatnm(), detail.getSvcstatnm())) {
            existingAct.setSvcstatnm(detail.getSvcstatnm());
            updated = true;
        }

        if (notEqual(existingAct.getSvcnm(), detail.getSvcnm())) {
            existingAct.setSvcnm(detail.getSvcnm());
            updated = true;
        }

        if (notEqual(existingAct.getPayatnm(), detail.getPayatnm())) {
            existingAct.setPayatnm(detail.getPayatnm());
            updated = true;
        }

        if (notEqual(existingAct.getPlacenm(), detail.getPlacenm())) {
            existingAct.setPlacenm(detail.getPlacenm());
            updated = true;
        }

        if (notEqual(existingAct.getUsetgtinfo(), detail.getUsetgtinfo())) {
            existingAct.setUsetgtinfo(detail.getUsetgtinfo());
            updated = true;
        }

        if (notEqual(existingAct.getSvcurl(), detail.getSvcurl())) {
            existingAct.setSvcurl(detail.getSvcurl());
            updated = true;
        }

        if (notEqual(existingAct.getX(), detail.getX())) {
            existingAct.setX(detail.getX());
            updated = true;
        }

        if (notEqual(existingAct.getY(), detail.getY())) {
            existingAct.setY(detail.getY());
            updated = true;
        }

        if (notEqual(existingAct.getSvcopnbgndt(), detail.getSvcopnbgndt())) {
            existingAct.setSvcopnbgndt(detail.getSvcopnbgndt());
            updated = true;
        }

        if (notEqual(existingAct.getSvcopnenddt(), detail.getSvcopnenddt())) {
            existingAct.setSvcopnenddt(detail.getSvcopnenddt());
            updated = true;
        }

        if (notEqual(existingAct.getRcptbgndt(), detail.getRcptbgndt())) {
            existingAct.setRcptbgndt(detail.getRcptbgndt());
            updated = true;
        }

        if (notEqual(existingAct.getRcptenddt(), detail.getRcptenddt())) {
            existingAct.setRcptenddt(detail.getRcptenddt());
            updated = true;
        }

        if (notEqual(existingAct.getImgurl(), detail.getImgurl())) {
            existingAct.setImgurl(detail.getImgurl());
            updated = true;
        }

        if (notEqual(existingAct.getVmin(), detail.getVmin())) {
            existingAct.setVmin(detail.getVmin());
            updated = true;
        }

        if (notEqual(existingAct.getVmax(), detail.getVmax())) {
            existingAct.setVmax(detail.getVmax());
            updated = true;
        }

        if (notEqual(existingAct.getRevstddaynm(), detail.getRevstddaynm())) {
            existingAct.setRevstddaynm(detail.getRevstddaynm());
            updated = true;
        }

        if (notEqual(existingAct.getRevstdday(), detail.getRevstdday())) {
            existingAct.setRevstdday(detail.getRevstdday());
            updated = true;
        }

        if (updated) {
            activityRepository.save(existingAct);
        }
    }

    private boolean notEqual(String existingValue, String newValue) {

        return existingValue == null ? newValue != null : !existingValue.equals(newValue);
    }

}
