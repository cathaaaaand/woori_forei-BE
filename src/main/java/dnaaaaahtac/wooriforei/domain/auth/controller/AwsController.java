package dnaaaaahtac.wooriforei.domain.auth.controller;

import dnaaaaahtac.wooriforei.global.common.CommonResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
public class AwsController {

    @GetMapping("/aws")
    public ResponseEntity<CommonResponse<Void>> awsfucking() {

        return ResponseEntity.ok().body(CommonResponse.of("awsfucking", null));
    }

    @GetMapping("/")
    public ResponseEntity<CommonResponse<Void>> wooriforei() {

        return ResponseEntity.ok().body(CommonResponse.of("hello wooriforei", null));
    }

    @GetMapping("/test-cors")
    public ResponseEntity<String> testCors() {

        HttpHeaders headers = new HttpHeaders();
        headers.add("Access-Control-Allow-Origin", "http://localhost:3000");

        return new ResponseEntity<>("CORS test response", headers, HttpStatus.OK);
    }

}
