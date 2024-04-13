package dnaaaaahtac.wooriforei.domain.user.controller;

import dnaaaaahtac.wooriforei.domain.user.dto.UserProfileResponseDTO;
import dnaaaaahtac.wooriforei.domain.user.service.UserService;
import dnaaaaahtac.wooriforei.global.common.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
