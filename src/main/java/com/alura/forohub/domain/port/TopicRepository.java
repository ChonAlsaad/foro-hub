package com.alura.forohub.domain.port;

import com.alura.forohub.domain.model.Topic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface TopicRepository {
    Topic save(Topic topic);
    Page<Topic> findAll(Pageable pageable);
    Optional<Topic> findById(Long id);
    boolean existsByTitleAndMessage(String title, String message);
    void deleteById(Long id);
    
    @Query("SELECT t FROM Topic t JOIN FETCH t.author JOIN FETCH t.course")
    Page<Topic> findAllWithAuthorAndCourse(Pageable pageable);
    
    @Query("SELECT t FROM Topic t JOIN FETCH t.author JOIN FETCH t.course WHERE t.id = :id")
    Optional<Topic> findByIdWithAuthorAndCourse(@Param("id") Long id);
}
