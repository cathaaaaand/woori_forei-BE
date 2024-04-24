package dnaaaaahtac.wooriforei.domain.image.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import dnaaaaahtac.wooriforei.domain.image.dto.ImageSaveDTO;
import dnaaaaahtac.wooriforei.domain.image.entity.Image;
import dnaaaaahtac.wooriforei.domain.image.repository.ImageRepository;
import dnaaaaahtac.wooriforei.domain.user.entity.User;
import dnaaaaahtac.wooriforei.domain.user.repository.UserRepository;
import dnaaaaahtac.wooriforei.global.exception.CustomException;
import dnaaaaahtac.wooriforei.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageService {

    @Value("${S3_NAME}")
    private String bucketName;

    private final AmazonS3Client amazonS3Client;
    private final ImageRepository imageRepository;
    private final UserRepository userRepository;

    // 프로필 저장
    @Transactional
    public List<String> saveProfileImages(ImageSaveDTO saveDto, User user) {
        List<String> resultList = new ArrayList<>();

        if (!(user.getImage() == null)) {
            deleteProfileImage(user);
        }

        for (MultipartFile multipartFile : saveDto.getImages()) {
            String value = saveImagePr(multipartFile, user);
            resultList.add(value);
        }

        return resultList;
    }

    // 프로필 저장
    @Transactional
    public String saveImagePr(MultipartFile multipartFile, User user) {

        long fileSize = multipartFile.getSize();
        double fileSizeCheck = fileSize / (1024.0 * 1024.0);

        // 1MB 제한 조건 확인
        if (fileSizeCheck > 1) {
            throw new CustomException(ErrorCode.OVER_SIZE_FILE);
        }

        String originalName = multipartFile.getOriginalFilename();
        Image image = new Image(originalName);

        String filename = image.getStoredName();

        try {
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentType(multipartFile.getContentType());
            objectMetadata.setContentLength(multipartFile.getInputStream().available());

            amazonS3Client.putObject(bucketName, filename, multipartFile.getInputStream(), objectMetadata);
            String accessUrl = amazonS3Client.getUrl(bucketName, filename).toString();
            image.setAccessUrl(accessUrl);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        imageRepository.save(image);

        user.setImage(image);
        userRepository.save(user);

        return image.getAccessUrl();
    }

    //프로필 조회
    public String getProfileImage(User user) {

        if (user.getImage() == null) {
            throw new CustomException(ErrorCode.NOT_FOUND_IMAGE);
        }

        return user.getImage().getAccessUrl();
    }


    // S3 프로필 이미지 삭제
    @Transactional
    public void deleteProfileImage(User user) {

        if (user.getImage() == null) {
            throw new CustomException(ErrorCode.NOT_FOUND_IMAGE);
        }

        Image image = user.getImage();

        DeleteObjectRequest request = new DeleteObjectRequest(bucketName, image.getStoredName());
        amazonS3Client.deleteObject(request); // S3에서 이미지 삭제

        user.setImage(null);

        imageRepository.delete(image);

        userRepository.save(user);

    }

}

