package com.jerimkaura.oasis.repository;

import com.jerimkaura.oasis.domain.Church;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ChurchRepository extends JpaRepository<Church, Long> {
    Church findChurchByName(String churchName);
    Church findChurchById(Long id);
    List<Church> findAll();
}