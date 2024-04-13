package dnaaaaahtac.wooriforei.domain.user.controller;

import dnaaaaahtac.wooriforei.domain.user.dto.UserProfileResponseDTO;
import dnaaaaahtac.wooriforei.domain.user.dto.UserProfileUpdateRequestDTO;
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
    public ResponseEntity<CommonResponse<UserProfileResponseDTO>> getUserProfile(
            @PathVariable Long userId) {

        UserProfileResponseDTO userProfileResponseDTO = userService.getUserProfile(userId);

        return ResponseEntity.ok()
                .body(CommonResponse.of("프로필 조회 성공", userProfileResponseDTO));
    }

    @PatchMapping("/{userId}")
    public ResponseEntity<CommonResponse<Void>> updateUserProfile(
            @PathVariable Long userId,
            @RequestBody UserProfileUpdateRequestDTO userProfileUpdateRequestDTO) {

        userService.updateUserProfile(userId, userProfileUpdateRequestDTO);

        return ResponseEntity.ok(CommonResponse.of("프로필 수정 성공", null));
    }
}
