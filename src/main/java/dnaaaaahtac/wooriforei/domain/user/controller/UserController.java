package dnaaaaahtac.wooriforei.domain.user.controller;

import dnaaaaahtac.wooriforei.domain.user.dto.PasswordCheckRequestDTO;
import dnaaaaahtac.wooriforei.domain.user.dto.PasswordUpdateRequestDTO;
import dnaaaaahtac.wooriforei.domain.user.dto.ProfileRequestDTO;
import dnaaaaahtac.wooriforei.domain.user.dto.ProfileResponseDTO;
import dnaaaaahtac.wooriforei.domain.user.service.UserService;
import dnaaaaahtac.wooriforei.global.common.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @GetMapping("/{userId}")
    public ResponseEntity<CommonResponse<ProfileResponseDTO>> getProfile(
            @PathVariable Long userId) {

        ProfileResponseDTO profileResponseDTO = userService.getProfile(userId);

        return ResponseEntity.ok()
                .body(CommonResponse.of("프로필 조회 성공", profileResponseDTO));
    }

    @PatchMapping("/{userId}")
    public ResponseEntity<CommonResponse<Void>> updateProfile(
            @PathVariable Long userId,
            @RequestBody ProfileRequestDTO profileRequestDTO) {

        userService.updateProfile(userId, profileRequestDTO);

        return ResponseEntity.ok(CommonResponse.of("프로필 수정 성공", null));
    }

    @PatchMapping("/{userId}/password")
    public ResponseEntity<CommonResponse<Void>> updatePassword(
            @PathVariable Long userId,
            @RequestBody PasswordUpdateRequestDTO passwordUpdateRequestDTO) {

        userService.updatePassword(userId, passwordUpdateRequestDTO);

        return ResponseEntity.ok(CommonResponse.of("비밀번호 수정 성공", null));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<CommonResponse<Void>> deleteUser(
            @PathVariable Long userId,
            @RequestBody PasswordCheckRequestDTO passwordCheckRequestDTO) {

        userService.deleteUser(userId, passwordCheckRequestDTO.getPassword());

        return ResponseEntity.ok(CommonResponse.of("회원 탈퇴 성공", null));
    }
}
