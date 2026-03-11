package com.alura.forohub.application.service;

import com.alura.forohub.domain.exception.DuplicateTopicException;
import com.alura.forohub.domain.exception.ResourceNotFoundException;
import com.alura.forohub.domain.model.Course;
import com.alura.forohub.domain.model.Topic;
import com.alura.forohub.domain.model.TopicStatus;
import com.alura.forohub.domain.model.User;
import com.alura.forohub.domain.port.ResponseRepository;
import com.alura.forohub.domain.port.TopicRepository;
import com.alura.forohub.infrastructure.persistence.JpaCourseRepository;
import com.alura.forohub.presentation.dto.topic.CreateTopicRequest;
import com.alura.forohub.presentation.dto.topic.TopicResponse;
import com.alura.forohub.presentation.dto.topic.UpdateTopicRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TopicService {

    private final TopicRepository topicRepository;
    private final JpaCourseRepository courseRepository;
    private final ResponseRepository responseRepository;

    public TopicService(TopicRepository topicRepository, JpaCourseRepository courseRepository, ResponseRepository responseRepository) {
        this.topicRepository = topicRepository;
        this.courseRepository = courseRepository;
        this.responseRepository = responseRepository;
    }

    @Transactional
    public TopicResponse create(CreateTopicRequest request, User author) {
        if (topicRepository.existsByTitleAndMessage(request.title(), request.message())) {
            throw new DuplicateTopicException("Ya existe un tópico con el mismo título y mensaje");
        }

        Course course = courseRepository.findById(request.courseId())
            .orElseThrow(() -> new ResourceNotFoundException("Curso no encontrado: " + request.courseId()));

        Topic topic = Topic.builder()
            .title(request.title())
            .message(request.message())
            .author(author)
            .course(course)
            .build();

        return TopicResponse.from(topicRepository.save(topic));
    }

    @Transactional(readOnly = true)
    public Page<TopicResponse> findAll(Pageable pageable) {
        return topicRepository.findAllWithAuthorAndCourse(pageable)
            .map(TopicResponse::from);
    }

    @Transactional(readOnly = true)
    public TopicResponse findById(Long id) {
        return topicRepository.findByIdWithAuthorAndCourse(id)
            .map(TopicResponse::from)
            .orElseThrow(() -> new ResourceNotFoundException("Tópico no encontrado: " + id));
    }

    @Transactional
    public TopicResponse update(Long id, UpdateTopicRequest request) {
        Topic topic = topicRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Tópico no encontrado: " + id));

        if (request.title() != null) topic.setTitle(request.title());
        if (request.message() != null) topic.setMessage(request.message());
        if (request.status() != null) topic.setStatus(TopicStatus.valueOf(request.status()));

        return TopicResponse.from(topicRepository.save(topic));
    }

    @Transactional
    public void delete(Long id) {
        Topic topic = topicRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Topic no encontrado con id: " + id));
        
        // Borrar responses asociadas antes de borrar el topic (integridad referencial)
        responseRepository.deleteByTopicId(id);
        
        topicRepository.deleteById(id);
    }
}
