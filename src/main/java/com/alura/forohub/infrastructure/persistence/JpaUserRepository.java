package com.alura.forohub.infrastructure.persistence;

import com.alura.forohub.domain.model.User;
import com.alura.forohub.domain.port.UserRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JpaUserRepository extends JpaRepository<User, Long>, UserRepository {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
}
