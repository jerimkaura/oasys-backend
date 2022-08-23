package com.jerimkaura.oasis.web.models.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
public class UploadProfilePictureDto {
    private Long id;
    private MultipartFile profileImage;
}
