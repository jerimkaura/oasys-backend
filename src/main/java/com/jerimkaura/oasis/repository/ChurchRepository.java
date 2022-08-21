package com.jerimkaura.oasis.repository;

import com.jerimkaura.oasis.domain.Church;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChurchRepository extends JpaRepository<Church, Long> {
    Church findChurchByName(String churchName);
    Church findChurchById(Long id);
}