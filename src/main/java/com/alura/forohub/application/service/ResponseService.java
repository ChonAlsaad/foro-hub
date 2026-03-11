package com.alura.forohub.application.service;

import com.alura.forohub.domain.exception.ResourceNotFoundException;
import com.alura.forohub.domain.model.Response;
import com.alura.forohub.domain.model.Topic;
import com.alura.forohub.domain.model.User;
import com.alura.forohub.domain.port.ResponseRepository;
import com.alura.forohub.domain.port.TopicRepository;
import com.alura.forohub.presentation.dto.response.CreateResponseRequest;
import com.alura.forohub.presentation.dto.response.ResponseDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ResponseService {

    private final ResponseRepository responseRepository;
    private final TopicRepository topicRepository;

    public ResponseService(ResponseRepository responseRepository, TopicRepository topicRepository) {
        this.responseRepository = responseRepository;
        this.topicRepository = topicRepository;
    }

    @Transactional
    public ResponseDto create(CreateResponseRequest request, User author) {
        Topic topic = topicRepository.findById(request.topicId())
            .orElseThrow(() -> new ResourceNotFoundException("Tópico no encontrado: " + request.topicId()));

        Response response = Response.builder()
            .message(request.message())
            .topic(topic)
            .author(author)
            .build();

        return ResponseDto.from(responseRepository.save(response));
    }

    @Transactional(readOnly = true)
    public List<ResponseDto> findByTopicId(Long topicId) {
        return responseRepository.findByTopicIdWithAuthor(topicId)
            .stream()
            .map(ResponseDto::from)
            .toList();
    }
}
