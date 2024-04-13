package dnaaaaahtac.wooriforei.domain.auth.controller;

import dnaaaaahtac.wooriforei.domain.auth.dto.LoginRequestDTO;
import dnaaaaahtac.wooriforei.domain.auth.dto.LoginResponseDTO;
import dnaaaaahtac.wooriforei.domain.auth.dto.RegisterRequestDTO;
import dnaaaaahtac.wooriforei.domain.auth.service.AuthService;
import dnaaaaahtac.wooriforei.global.Jwt.JwtUtil;
import dnaaaaahtac.wooriforei.global.common.CommonResponse;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final JwtUtil jwtUtil;

    @PostMapping("/signup")
    public ResponseEntity<CommonResponse<Void>> register(
            @RequestBody @Valid RegisterRequestDTO registerRequestDTO) {

        authService.register(registerRequestDTO);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CommonResponse.of("회원가입 성공", null));
    }

    @PostMapping("/login")
    public ResponseEntity<CommonResponse<LoginResponseDTO>> login(
            @RequestBody @Valid LoginRequestDTO requestDTO, HttpServletResponse response) {

        LoginResponseDTO loginResponseDTO = authService.login(requestDTO);
        String jwtToken = jwtUtil.createToken(loginResponseDTO.getUserId().toString());
        response.setHeader(HttpHeaders.AUTHORIZATION, jwtToken);

        return ResponseEntity.ok()
                .body(CommonResponse.of("로그인 성공", loginResponseDTO));
    }

}
