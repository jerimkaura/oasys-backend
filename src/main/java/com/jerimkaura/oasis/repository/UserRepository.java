package com.jerimkaura.oasis.repository;

import com.jerimkaura.oasis.domain.Church;
import com.jerimkaura.oasis.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    User findUserByUsername(String username);
    List<User> findUserByChurch(Church church);
    List<User> findAll();
    User findUserById(Long id);
}
