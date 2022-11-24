package com.jerimkaura.oasis.web.api;


import com.jerimkaura.oasis.domain.User;
import com.jerimkaura.oasis.service.song.SongService;
import com.jerimkaura.oasis.service.user.UserService;
import com.jerimkaura.oasis.web.BaseResponse;
import com.jerimkaura.oasis.web.api.models.requests.SaveSongDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Slf4j
public class SongController {
    private final SongService songService;
    private final UserService userService;

    @PostMapping(
            path = "/songs/upload",
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}
    )
    ResponseEntity<BaseResponse<?>> saveSong(
            @RequestParam("songTitle") String songTitle,
            @RequestParam("releaserYear") String releaseYear,
            @RequestParam("audioFile") MultipartFile audioFile,
            @RequestParam("artist") Long artistId
    ) {
        SaveSongDto saveSongDto = new SaveSongDto(null, songTitle, releaseYear, audioFile, artistId);
        return new ResponseEntity<>(
                new BaseResponse<>(
                        "Song uploaded successfully!",
                        HttpStatus.CREATED.value(),
                        songService.saveSong(saveSongDto)
                ),
                HttpStatus.CREATED
        );
    }

    @GetMapping("/songs")
    ResponseEntity<BaseResponse<?>> getSongs() {
        return new ResponseEntity<>(
                new BaseResponse<>(
                        "Success",
                        HttpStatus.OK.value(),
                        songService.getSongs()
                ),
                HttpStatus.OK
        );
    }

    @GetMapping("/songs/artist/{id}")
    ResponseEntity<BaseResponse<?>> getSongsByArtist(@PathVariable Long id) {
        try {
            User user = userService.getUserById(id);
            return new ResponseEntity<>(
                    new BaseResponse<>(
                            "Success",
                            HttpStatus.OK.value(),
                            songService.getSongsByArtist(user)
                    ),
                    HttpStatus.OK
            );
        } catch (Exception e) {
            return new ResponseEntity<>(
                    new BaseResponse<>(
                            "Success",
                            HttpStatus.OK.value(),
                            e.getMessage()
                    ),
                    HttpStatus.OK
            );
        }

    }

    @GetMapping("/songs/{id}")
    ResponseEntity<BaseResponse<?>> getSongById(@PathVariable Long id) {
        return new ResponseEntity<>(
                new BaseResponse<>(
                        "Success",
                        HttpStatus.OK.value(),
                        songService.getSong(id)
                ),
                HttpStatus.OK
        );
    }

    @DeleteMapping("/songs/{id}")
    ResponseEntity<BaseResponse<?>> deleteSongById(@PathVariable Long id) {
        songService.deleteSong(id);
        return new ResponseEntity<>(
                new BaseResponse<>(
                        "Success",
                        HttpStatus.NO_CONTENT.value(),
                        "Song deleted!"
                ),
                HttpStatus.NO_CONTENT
        );
    }

}
