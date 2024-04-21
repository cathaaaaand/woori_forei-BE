package dnaaaaahtac.wooriforei.domain.image.controller;

import dnaaaaahtac.wooriforei.domain.image.dto.ImageSaveDTO;
import dnaaaaahtac.wooriforei.domain.image.service.ImageService;
import dnaaaaahtac.wooriforei.global.common.CommonResponse;
import dnaaaaahtac.wooriforei.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ImageController {

    private final ImageService imageService;


    //프로필 업로드
    @PostMapping("/images/profile")
    public ResponseEntity<CommonResponse<List<String>>> saveProfileImage(
            @ModelAttribute ImageSaveDTO imageSaveDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {

        List<String> list = imageService.saveProfileImages(imageSaveDto, userDetails.getUser());

        return ResponseEntity.ok().body(CommonResponse.of("프로필 이미지 저장 성공", list));
    }

    //프로필 사진 조회
    @GetMapping("/images/profile")
    public ResponseEntity<CommonResponse<String>> getProfileImage(
            @AuthenticationPrincipal UserDetailsImpl userDetails) {

        String url = imageService.getProfileImage(userDetails.getUser());

        return ResponseEntity.ok().body(CommonResponse.of("프로필 사진 조회 성공", url));
    }

    //프로필 사진 삭제
    @DeleteMapping("/images/profile")
    public ResponseEntity<CommonResponse<String>> deleteProfileImage(
            @AuthenticationPrincipal UserDetailsImpl userDetails) {

        imageService.deleteProfileImage(userDetails.getUser());

        return ResponseEntity.ok().body(CommonResponse.of("프로필 사진 삭제 성공", null));
    }


/*
    //게시글 사진 업로드
    @PostMapping("/image")
    public ResponseEntity<CommonResponse<List<String>>> saveBoardImage(
            @ModelAttribute ImageSaveDTO imageSaveDto, Board board) {

        List<String> list = imageService.saveBoardImages(imageSaveDto,board);

        return ResponseEntity.ok().body(CommonResponse.of("게시글 이미지 저장 성공", list));
    }
*/


/*
    //게시글 사진 조회
    @GetMapping("/images")
    public String getallImage(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return imageService.getImage("img.png");
    }

    //게시글 사진 삭제
    @PostMapping("/images")
    public String delete(@RequestParam(required = true) String fileName){
        imageService.deleteImage(fileName);
        return "success";
    }*/
}