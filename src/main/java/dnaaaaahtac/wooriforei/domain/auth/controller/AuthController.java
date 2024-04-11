package dnaaaaahtac.wooriforei.domain.auth.controller;

import dnaaaaahtac.wooriforei.domain.auth.dto.LoginRequestDTO;
import dnaaaaahtac.wooriforei.domain.auth.dto.LoginUserResponseDTO;
import dnaaaaahtac.wooriforei.domain.auth.dto.RegisterAdminRequestDTO;
import dnaaaaahtac.wooriforei.domain.auth.dto.RegisterUserRequestDTO;
import dnaaaaahtac.wooriforei.domain.auth.service.AuthService;
import dnaaaaahtac.wooriforei.global.Jwt.JwtUtil;
import dnaaaaahtac.wooriforei.global.common.CommonResponse;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private final PasswordEncoder passwordEncoder;

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

    @PostMapping("/login")
    public ResponseEntity<CommonResponse<LoginUserResponseDTO>> login(
            @RequestBody @Valid LoginRequestDTO requestDTO, HttpServletResponse response) {

        LoginUserResponseDTO loginUserResponseDTO = authService.loginUser(requestDTO);
        String jwtToken = jwtUtil.createToken(loginUserResponseDTO.getUsername());
        response.setHeader(HttpHeaders.AUTHORIZATION, jwtToken);

        return ResponseEntity.ok()
                .body(CommonResponse.of("사용자 로그인 성공", loginUserResponseDTO));
    }

}
