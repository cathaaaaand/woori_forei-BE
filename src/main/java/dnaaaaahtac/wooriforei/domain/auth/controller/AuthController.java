package dnaaaaahtac.wooriforei.domain.auth.controller;

import dnaaaaahtac.wooriforei.domain.auth.dto.*;
import dnaaaaahtac.wooriforei.domain.auth.service.AuthService;
import dnaaaaahtac.wooriforei.domain.auth.service.EmailVerificationService;
import dnaaaaahtac.wooriforei.global.Jwt.JwtUtil;
import dnaaaaahtac.wooriforei.global.common.CommonResponse;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/*@CrossOrigin(
        origins = {"https://cat.wooriforei.info", "http://localhost:3000", "https://www.wooriforei.info"},
        allowCredentials = "true",
        allowedHeaders = {"Authorization", "Content-Type", "Accept"},
        methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS}
)*/
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final EmailVerificationService emailVerificationService;
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

    @PostMapping("/send-verification-email")
    public ResponseEntity<CommonResponse<Void>> sendVerificationEmail(
            @RequestBody @Valid VerificationEmailCodeRequestDTO verificationEmailCodeRequestDTO) {

        emailVerificationService.sendVerificationEmail(verificationEmailCodeRequestDTO.getEmail());

        return ResponseEntity.ok(CommonResponse.of("이메일 인증 코드 발송 성공", null));
    }


    @PostMapping("/verify-email")
    public ResponseEntity<CommonResponse<Void>> verifyEmail(
            @RequestBody VerificationEmailCodeRequestDTO verificationEmailCodeRequestDTO) {

        emailVerificationService.verifyEmail(
                verificationEmailCodeRequestDTO.getEmail(),
                verificationEmailCodeRequestDTO.getVerificationCode());

        return ResponseEntity.ok(CommonResponse.of("이메일 인증 성공", null));
    }

    @GetMapping("/google-login")
    public ResponseEntity<CommonResponse<GoogleLoginResponseDTO>> googleLogin(
            @RequestParam("code") String code) {

        GoogleLoginResponseDTO responseDTO = authService.googleLogin(code);

        return ResponseEntity.ok().body(CommonResponse.of("구글 로그인 성공", responseDTO));
    }


}
