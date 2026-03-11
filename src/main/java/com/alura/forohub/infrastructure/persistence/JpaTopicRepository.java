package com.alura.forohub.infrastructure.persistence;

import com.alura.forohub.domain.model.Topic;
import com.alura.forohub.domain.port.TopicRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JpaTopicRepository extends JpaRepository<Topic, Long>, TopicRepository {
    boolean existsByTitleAndMessage(String title, String message);
    
    @Query("SELECT t FROM Topic t JOIN FETCH t.author JOIN FETCH t.course")
    Page<Topic> findAllWithAuthorAndCourse(Pageable pageable);
    
    @Query("SELECT t FROM Topic t JOIN FETCH t.author JOIN FETCH t.course WHERE t.id = :id")
    Optional<Topic> findByIdWithAuthorAndCourse(@Param("id") Long id);
}
