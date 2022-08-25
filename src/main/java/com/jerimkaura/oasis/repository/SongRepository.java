package com.jerimkaura.oasis.repository;

import com.jerimkaura.oasis.domain.Song;
import com.jerimkaura.oasis.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SongRepository extends JpaRepository<Song, Long> {
    Song findSongById(Long id);
    List<Song> findSongByArtist(User artist);
}