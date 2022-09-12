package com.jerimkaura.oasis.repository;

import com.jerimkaura.oasis.domain.Church;
import com.jerimkaura.oasis.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    User findUserByUsername(String username);
    List<User> findUserByChurch(Church church);
    List<User> findAll();
    User findUserById(Long id);

    @Transactional
    @Modifying
    @Query("UPDATE User a " +
            "SET a.verified = TRUE WHERE a.username = ?1")
    int verifyAccount(String username);
}
