package dnaaaaahtac.wooriforei.domain.openapi.controller;


import dnaaaaahtac.wooriforei.domain.openapi.dto.InformationResponseWrapper;
import dnaaaaahtac.wooriforei.domain.openapi.service.InformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;


@RestController
@RequestMapping("/api/openAPI")
public class OpenApiController {

    private final InformationService informationService;

    @Autowired
    public OpenApiController(InformationService informationService) {
        this.informationService = informationService;
    }

    @GetMapping("/information")
    public Mono<ResponseEntity<InformationResponseWrapper>> information() {
        return informationService.retrieveInformation()
                .map(response -> ResponseEntity.ok().body(response))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}
