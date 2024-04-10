package dnaaaaahtac.wooriforei.domain.auth.controller;

import dnaaaaahtac.wooriforei.domain.auth.dto.RegisterAdminRequestDTO;
import dnaaaaahtac.wooriforei.domain.auth.dto.RegisterUserRequestDTO;
import dnaaaaahtac.wooriforei.domain.auth.service.AuthService;
import dnaaaaahtac.wooriforei.global.common.CommonResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/signup")
    public ResponseEntity<CommonResponse<Void>> registerUser(
            @RequestBody @Valid RegisterUserRequestDTO requestDTO) {

        authService.registerUser(requestDTO);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CommonResponse.of("사용자 회원가입 성공", null));
    }

    @PostMapping("/admin-signup")
    public ResponseEntity<CommonResponse<Void>> registerAdmin(
            @RequestBody RegisterAdminRequestDTO requestDTO) {

        authService.registerAdmin(requestDTO);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CommonResponse.of("관리자 회원가입 성공", null));
    }
}
