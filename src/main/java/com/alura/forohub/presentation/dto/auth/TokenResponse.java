package com.alura.forohub.presentation.dto.auth;

public record TokenResponse(String token, String type) {
    public TokenResponse(String token) {
        this(token, "Bearer");
    }
}
