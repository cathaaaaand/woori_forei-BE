package dnaaaaahtac.wooriforei.domain.auth.controller;

import dnaaaaahtac.wooriforei.global.common.CommonResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AwsController {

    @GetMapping("/aws")
    public ResponseEntity<CommonResponse<Void>> awsfucking() {

        return ResponseEntity.ok().body(CommonResponse.of("awsfucking", null));
    }

    @GetMapping("/")
    public String welcome() {
        return "Welcome to the wooriForei Backend API!";
    }

}