package com.jerimkaura.oasis.web.api;


import com.jerimkaura.oasis.service.church.ChurchService;
import com.jerimkaura.oasis.service.song.SongService;
import com.jerimkaura.oasis.service.user.UserService;
import com.jerimkaura.oasis.web.BaseResponse;
import com.jerimkaura.oasis.web.models.requests.SaveSongDto;
import com.jerimkaura.oasis.web.models.requests.UploadProfilePictureDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Slf4j
public class SongController {
    private final SongService songService;

    @PostMapping(
            path = "/songs/upload",
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}
    )
    ResponseEntity<BaseResponse<?>> saveSong(
            @RequestParam("songTitle") String songTitle,
            @RequestParam("releaserYear") String releaseYear,
            @RequestParam("audioFile") MultipartFile audioFile
    ) {
        SaveSongDto saveSongDto = new SaveSongDto(null, songTitle, releaseYear, audioFile);
        return new ResponseEntity<>(
                new BaseResponse<>(
                        "Song uploaded successfully!",
                        HttpStatus.CREATED.value(),
                        songService.saveSong(saveSongDto)
                ),
                HttpStatus.CREATED
        );
    }
}
