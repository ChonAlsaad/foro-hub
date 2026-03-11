package com.alura.forohub.presentation.controller;

import com.alura.forohub.application.service.ResponseService;
import com.alura.forohub.domain.model.User;
import com.alura.forohub.presentation.dto.response.CreateResponseRequest;
import com.alura.forohub.presentation.dto.response.ResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/responses")
@SecurityRequirement(name = "bearer-key")
@Tag(name = "Responses", description = "Gestión de respuestas a tópicos")
public class ResponseController {

    private final ResponseService responseService;

    public ResponseController(ResponseService responseService) {
        this.responseService = responseService;
    }

    @PostMapping
    @Operation(summary = "Crear una respuesta a un tópico")
    public ResponseEntity<ResponseDto> create(
            @RequestBody @Valid CreateResponseRequest request,
            @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(responseService.create(request, user));
    }

    @GetMapping("/topic/{topicId}")
    @Operation(summary = "Listar respuestas de un tópico")
    public ResponseEntity<List<ResponseDto>> findByTopicId(@PathVariable Long topicId) {
        return ResponseEntity.ok(responseService.findByTopicId(topicId));
    }
}
