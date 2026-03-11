package com.alura.forohub.application.service;

import com.alura.forohub.domain.exception.DuplicateTopicException;
import com.alura.forohub.domain.exception.ResourceNotFoundException;
import com.alura.forohub.domain.model.*;
import com.alura.forohub.domain.port.ResponseRepository;
import com.alura.forohub.domain.port.TopicRepository;
import com.alura.forohub.infrastructure.persistence.JpaCourseRepository;
import com.alura.forohub.presentation.dto.topic.CreateTopicRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("TopicService — Tests de unidad")
class TopicServiceTest {

    @Mock private TopicRepository topicRepository;
    @Mock private ResponseRepository responseRepository;
    @Mock private JpaCourseRepository courseRepository;

    @InjectMocks
    private TopicService topicService;

    @Test
    @DisplayName("Crear tópico válido retorna TopicResponse con datos correctos")
    void crearTopico_valido_retornaTopicResponse() {
        // Arrange
        var request = new CreateTopicRequest("Título válido", "Mensaje válido", 1L);

        var user = User.builder().id(1L).name("Admin").email("admin@forohub.com").build();
        var course = Course.builder().id(1L).name("Spring Boot").category("Backend").build();
        var topic = Topic.builder()
            .id(1L).title("Título válido").message("Mensaje válido")
            .status(TopicStatus.OPEN).author(user).course(course).build();

        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));
        when(topicRepository.existsByTitleAndMessage(any(), any())).thenReturn(false);
        when(topicRepository.save(any(Topic.class))).thenReturn(topic);

        // Act
        var response = topicService.create(request, user);

        // Assert
        assertThat(response.title()).isEqualTo("Título válido");
        assertThat(response.authorName()).isEqualTo("Admin");
        assertThat(response.courseName()).isEqualTo("Spring Boot");
        assertThat(response.status()).isEqualTo(TopicStatus.OPEN);
        verify(topicRepository).save(any(Topic.class));
    }

    @Test
    @DisplayName("Crear tópico duplicado lanza DuplicateTopicException")
    void crearTopico_duplicado_lanzaExcepcion() {
        // Arrange
        var request = new CreateTopicRequest("Título duplicado", "Mensaje duplicado", 1L);
        var user = User.builder().id(1L).name("Admin").email("admin@forohub.com").build();
        when(topicRepository.existsByTitleAndMessage("Título duplicado", "Mensaje duplicado"))
            .thenReturn(true);

        // Act & Assert
        assertThatThrownBy(() -> topicService.create(request, user))
            .isInstanceOf(DuplicateTopicException.class);

        verify(topicRepository, never()).save(any());
    }

    @Test
    @DisplayName("findById con ID inexistente lanza ResourceNotFoundException")
    void findById_idInexistente_lanzaExcepcion() {
        // Arrange
        when(topicRepository.findByIdWithAuthorAndCourse(99L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> topicService.findById(99L))
            .isInstanceOf(ResourceNotFoundException.class);
    }
}
