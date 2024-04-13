package dnaaaaahtac.wooriforei.domain.openapi.service;

import dnaaaaahtac.wooriforei.domain.openapi.dto.seoulgoods.SeoulGoodsDetailDTO;
import dnaaaaahtac.wooriforei.domain.openapi.dto.seoulgoods.SeoulGoodsResponseDTO;
import dnaaaaahtac.wooriforei.domain.openapi.entity.SeoulGoods;
import dnaaaaahtac.wooriforei.domain.openapi.repository.SeoulGoodsRepository;
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
public class SeoulGoodsService {

    private final WebClient webClient;
    private final String authKey;
    private final SeoulGoodsRepository seoulGoodsRepository;

    @Autowired
    public SeoulGoodsService(
            WebClient.Builder webClientBuilder,
            @Value("${AUTH_KEY}") String authKey,
            SeoulGoodsRepository seoulGoodsRepository) {

        this.webClient = webClientBuilder.baseUrl("http://openapi.seoul.go.kr:8088").build();
        this.authKey = authKey;
        this.seoulGoodsRepository = seoulGoodsRepository;
    }

    public Mono<SeoulGoodsResponseDTO> retrieveSeoulGoods() {

        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/{authKey}/{responseType}/{serviceName}/{startIdx}/{endIdx}")
                        .build(authKey, "json", "frgnrTourGiftShopInfo", 1, 999))
                .header("Content-type", "application/json")
                .retrieve()
                .bodyToMono(SeoulGoodsResponseDTO.class)
                .doOnSuccess(response -> {
                    System.out.println("Successfully retrieved data");
                    saveSeoulGoodsDetails(response);
                })
                .doOnError(e -> System.out.println("Error: " + e.getMessage()));
    }


    // 전체 조회
    public List<SeoulGoods> findAllSeoulGoods() {

        return seoulGoodsRepository.findAll();
    }

    // 단건 조회
    public Mono<SeoulGoods> findInSeoulGoodsById(Long id) {

        Optional<SeoulGoods> SeoulgoodsOptional = seoulGoodsRepository.findById(id);

        // 정보를 찾을 수 없는 경우 예외 발생
        if (SeoulgoodsOptional.isEmpty()) {
            return Mono.error(new CustomException(ErrorCode.NOT_FOUND_DATA));
        }

        return Mono.justOrEmpty(SeoulgoodsOptional);
    }

    @Transactional
    public void saveSeoulGoodsDetails(SeoulGoodsResponseDTO seoulgoodsResponseDto) {

        seoulgoodsResponseDto.getFrgnrTourGiftShopInfo().getRow().forEach(this::convertAndSave);
    }

    @Transactional
    public void convertAndSave(SeoulGoodsDetailDTO detail) {

        seoulGoodsRepository.findByNmAndTel(detail.getNm(), detail.getTel())
                .ifPresentOrElse(existingGoods -> updateExistingSeoulGoods(existingGoods, detail),
                        () -> seoulGoodsRepository.save(mapToEntity(new SeoulGoods(), detail)));
    }

    private SeoulGoods mapToEntity(SeoulGoods seoulGoods, SeoulGoodsDetailDTO detail) {
        // DTO -> Entity 매핑
        seoulGoods.setNm(detail.getNm());
        seoulGoods.setAddr(detail.getAddr());
        seoulGoods.setState(detail.getState());
        seoulGoods.setStopDt(detail.getStopDt());
        seoulGoods.setSuspensionStartDt(detail.getSuspensionStartDt());
        seoulGoods.setSuspensionEndDt(detail.getSuspensionEndDt());
        seoulGoods.setReOpenDt(detail.getReOpenDt());
        seoulGoods.setTel(detail.getTel());
        seoulGoods.setXcode(detail.getXcode());
        seoulGoods.setYcode(detail.getYcode());

        return seoulGoods;
    }

    private void updateExistingSeoulGoods(SeoulGoods existingGoods, SeoulGoodsDetailDTO detail) {
        boolean updated = false;

        if (notEqual(existingGoods.getNm(), detail.getNm())) {
            existingGoods.setNm(detail.getNm());
            updated = true;
        }

        if (notEqual(existingGoods.getAddr(), detail.getAddr())) {
            existingGoods.setAddr(detail.getAddr());
            updated = true;
        }

        if (notEqual(existingGoods.getState(), detail.getState())) {
            existingGoods.setState(detail.getState());
            updated = true;
        }

        if (notEqual(existingGoods.getStopDt(), detail.getStopDt())) {
            existingGoods.setStopDt(detail.getStopDt());
            updated = true;
        }

        if (notEqual(existingGoods.getSuspensionStartDt(), detail.getSuspensionStartDt())) {
            existingGoods.setSuspensionStartDt(detail.getSuspensionStartDt());
            updated = true;
        }

        if (notEqual(existingGoods.getSuspensionEndDt(), detail.getSuspensionEndDt())) {
            existingGoods.setSuspensionEndDt(detail.getSuspensionEndDt());
            updated = true;
        }

        if (notEqual(existingGoods.getReOpenDt(), detail.getReOpenDt())) {
            existingGoods.setReOpenDt(detail.getReOpenDt());
            updated = true;
        }

        if (notEqual(existingGoods.getTel(), detail.getTel())) {
            existingGoods.setTel(detail.getTel());
            updated = true;
        }

        if (notEqual(existingGoods.getXcode(), detail.getXcode())) {
            existingGoods.setXcode(detail.getXcode());
            updated = true;
        }

        if (notEqual(existingGoods.getYcode(), detail.getYcode())) {
            existingGoods.setYcode(detail.getYcode());
            updated = true;
        }

        if (updated) {
            seoulGoodsRepository.save(existingGoods);
        }
    }

    private boolean notEqual(String existingValue, String newValue) {

        return existingValue == null ? newValue != null : !existingValue.equals(newValue);
    }

}
