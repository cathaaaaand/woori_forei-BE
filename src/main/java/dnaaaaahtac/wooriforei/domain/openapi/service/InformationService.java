package dnaaaaahtac.wooriforei.domain.openapi.service;

import dnaaaaahtac.wooriforei.domain.openapi.dto.InformationResponseWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class InformationService {

    private final WebClient webClient;
    private final String authKey;

    @Autowired
    public InformationService(WebClient.Builder webClientBuilder,
                              @Value("${AUTH_KEY}") String authKey) {
        this.webClient = webClientBuilder.baseUrl("http://openapi.seoul.go.kr:8088").build();
        this.authKey = authKey;
    }

    public Mono<InformationResponseWrapper> retrieveInformation() {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/{authKey}/{responseType}/{serviceName}/{startIdx}/{endIdx}")
                        .build(authKey, "json", "TbTourInformation", 1, 999))
                .header("Content-type", "application/json")
                .retrieve()
                .bodyToMono(InformationResponseWrapper.class)
                .doOnSuccess(response -> System.out.println("Successfully retrieved data"))
                .doOnError(e -> System.out.println("Error: " + e.getMessage()));
    }
}