package com.alura.forohub.presentation.dto.response;

import com.alura.forohub.domain.model.Response;

import java.time.LocalDateTime;

public record ResponseDto(
    Long id,
    String message,
    LocalDateTime createdAt,
    String authorName,
    boolean solution
) {
    public static ResponseDto from(Response response) {
        return new ResponseDto(
            response.getId(),
            response.getMessage(),
            response.getCreatedAt(),
            response.getAuthor().getName(),
            response.isSolution()
        );
    }
}
