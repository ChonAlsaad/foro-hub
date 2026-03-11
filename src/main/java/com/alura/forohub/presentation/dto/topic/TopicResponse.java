package com.alura.forohub.presentation.dto.topic;

import com.alura.forohub.domain.model.Topic;
import com.alura.forohub.domain.model.TopicStatus;

import java.time.LocalDateTime;

public record TopicResponse(
    Long id,
    String title,
    String message,
    LocalDateTime createdAt,
    TopicStatus status,
    String authorName,
    String courseName
) {
    public static TopicResponse from(Topic topic) {
        return new TopicResponse(
            topic.getId(),
            topic.getTitle(),
            topic.getMessage(),
            topic.getCreatedAt(),
            topic.getStatus(),
            topic.getAuthor().getName(),
            topic.getCourse().getName()
        );
    }
}
