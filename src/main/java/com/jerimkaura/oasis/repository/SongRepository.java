package com.jerimkaura.oasis.repository;

import com.jerimkaura.oasis.domain.Song;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SongRepository extends JpaRepository<Song, Long> {
    Song findSongById(Long id);
}