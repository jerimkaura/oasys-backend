package com.jerimkaura.oasis.service.song;

import com.jerimkaura.oasis.domain.Song;
import com.jerimkaura.oasis.repository.SongRepository;
import com.jerimkaura.oasis.repository.UserRepository;
import com.jerimkaura.oasis.service.s3.FileStore;
import com.jerimkaura.oasis.web.models.dto.SongDto;
import com.jerimkaura.oasis.web.models.requests.SaveSongDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class SongServiceImpl implements SongService{
    private final UserRepository userRepository;
    private final SongRepository songRepository;
    private final FileStore fileStore;

    @Value("${oasis.aws-bucket-name}")
    private String bucketName;

    @Override
    public SongDto saveSong(SaveSongDto saveSongDto) {
        MultipartFile multipartFile = saveSongDto.getAudioFile();
        String songTitle = saveSongDto.getTitle();

        if (multipartFile.isEmpty()) {
            throw new IllegalStateException("Cannot upload empty file");
        }
        String[] strArray = songTitle.split(" ");
        String fileNameArray1 = String.join("-", strArray);
        log.error(fileNameArray1);
        String audioUrl = fileStore.uploadFile(
                fileNameArray1,
                bucketName,
                "songs",
                multipartFile
        );

        Song song = new Song(null, saveSongDto.getTitle(), "2000", audioUrl, null);
        log.error(audioUrl);
        return new ModelMapper().map(songRepository.save(song), SongDto.class);
    }

    @Override
    public SongDto getSong(Long id) {
        return null;
    }

    @Override
    public SongDto updateSong(Long id, SongDto songDto) {
        return null;
    }

    @Override
    public List<SongDto> getSongs() {
        return null;
    }

    @Override
    public void deleteSong(Long id) {

    }
}
