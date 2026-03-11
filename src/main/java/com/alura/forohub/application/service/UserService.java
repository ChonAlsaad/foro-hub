package com.alura.forohub.application.service;

import com.alura.forohub.domain.exception.DuplicateTopicException;
import com.alura.forohub.domain.model.User;
import com.alura.forohub.domain.port.UserRepository;
import com.alura.forohub.presentation.dto.user.CreateUserRequest;
import com.alura.forohub.presentation.dto.user.UserResponse;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public UserResponse create(CreateUserRequest request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new DuplicateTopicException("Ya existe un usuario con el email: " + request.email());
        }

        User user = User.builder()
            .name(request.name())
            .email(request.email())
            .password(passwordEncoder.encode(request.password()))
            .build();

        return UserResponse.from(userRepository.save(user));
    }
}
