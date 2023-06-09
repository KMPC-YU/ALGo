package com.kmpc.algobe.user.repository;

import com.kmpc.algobe.user.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByNickname(String nickname);
    Optional<User> findByEmail(String email);
    Boolean existsByNickname(String nickname);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
}
