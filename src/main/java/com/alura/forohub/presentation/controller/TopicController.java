package com.alura.forohub.presentation.controller;

import com.alura.forohub.application.service.TopicService;
import com.alura.forohub.domain.model.User;
import com.alura.forohub.presentation.dto.topic.CreateTopicRequest;
import com.alura.forohub.presentation.dto.topic.TopicResponse;
import com.alura.forohub.presentation.dto.topic.UpdateTopicRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/api/topics")
@SecurityRequirement(name = "bearer-key")
@Tag(name = "Topics", description = "CRUD de tópicos del foro")
public class TopicController {

    private final TopicService topicService;

    public TopicController(TopicService topicService) {
        this.topicService = topicService;
    }

    @PostMapping
    @Operation(summary = "Crear un nuevo tópico")
    public ResponseEntity<TopicResponse> create(
            @RequestBody @Valid CreateTopicRequest request,
            @AuthenticationPrincipal User user,
            UriComponentsBuilder uriBuilder) {
        TopicResponse response = topicService.create(request, user);
        var uri = uriBuilder.path("/api/topics/{id}").buildAndExpand(response.id()).toUri();
        return ResponseEntity.created(uri).body(response);
    }

    @GetMapping
    @Operation(summary = "Listar todos los tópicos con paginación")
    public ResponseEntity<Page<TopicResponse>> findAll(
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.ok(topicService.findAll(pageable));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener detalle de un tópico")
    public ResponseEntity<TopicResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(topicService.findById(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un tópico")
    public ResponseEntity<TopicResponse> update(
            @PathVariable Long id,
            @RequestBody @Valid UpdateTopicRequest request) {
        return ResponseEntity.ok(topicService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un tópico")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        topicService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
