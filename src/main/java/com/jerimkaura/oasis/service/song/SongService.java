package com.jerimkaura.oasis.service.song;

import com.jerimkaura.oasis.web.models.dto.SongDto;
import com.jerimkaura.oasis.web.models.requests.SaveSongDto;

import java.util.List;

public interface SongService {
    SongDto saveSong(SaveSongDto saveSongDto);

    SongDto getSong(Long id);

    SongDto updateSong(Long id, SongDto songDto);

    List<SongDto> getSongs();

    void deleteSong(Long id);
}
