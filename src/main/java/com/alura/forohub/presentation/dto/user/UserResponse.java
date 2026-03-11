package com.alura.forohub.presentation.dto.user;

import com.alura.forohub.domain.model.User;

public record UserResponse(Long id, String name, String email) {
    public static UserResponse from(User user) {
        return new UserResponse(user.getId(), user.getName(), user.getEmail());
    }
}
