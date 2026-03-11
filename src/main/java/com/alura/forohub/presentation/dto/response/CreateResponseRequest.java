package com.alura.forohub.presentation.dto.response;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateResponseRequest(
    @NotBlank String message,
    @NotNull Long topicId
) {}
