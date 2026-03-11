package com.alura.forohub.presentation.dto.topic;

public record UpdateTopicRequest(
    String title,
    String message,
    String status
) {}
