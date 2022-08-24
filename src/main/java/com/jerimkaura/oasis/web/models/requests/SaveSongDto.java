package com.jerimkaura.oasis.web.models.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
public class SaveSongDto {
    private  Long id;
    private  String title;
    private  String releaseYear;
    private MultipartFile audioFile;
    private Long artistId;
}
