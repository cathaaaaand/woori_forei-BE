package dnaaaaahtac.wooriforei.domain.image.controller;

import dnaaaaahtac.wooriforei.domain.image.service.ImageService;
import dnaaaaahtac.wooriforei.global.common.CommonResponse;
import dnaaaaahtac.wooriforei.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/images/profile")
@CrossOrigin(origins = {"https://cat.wooriforei.info/", "http://localhost:3000", "https://www.wooriforei.info/"})
public class ImageController {

    private final ImageService imageService;


    //프로필 업로드
    @PostMapping
    public ResponseEntity<CommonResponse<List<String>>> saveProfileImage(
            @RequestPart(required = false, name = "images") List<MultipartFile> images,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {

        List<String> list = imageService.saveProfileImages(images, userDetails.getUser());

        return ResponseEntity.ok().body(CommonResponse.of("프로필 이미지 저장 성공", list));
    }

    //프로필 사진 조회
    @GetMapping
    public ResponseEntity<CommonResponse<String>> getProfileImage(
            @AuthenticationPrincipal UserDetailsImpl userDetails) {

        String url = imageService.getProfileImage(userDetails.getUser());

        return ResponseEntity.ok().body(CommonResponse.of("프로필 사진 조회 성공", url));
    }

    //프로필 사진 삭제
    @DeleteMapping
    public ResponseEntity<CommonResponse<String>> deleteProfileImage(
            @AuthenticationPrincipal UserDetailsImpl userDetails) {

        imageService.deleteProfileImage(userDetails.getUser());

        return ResponseEntity.ok().body(CommonResponse.of("프로필 사진 삭제 성공", null));
    }

}