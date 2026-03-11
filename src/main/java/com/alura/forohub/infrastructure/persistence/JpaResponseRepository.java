package com.alura.forohub.infrastructure.persistence;

import com.alura.forohub.domain.model.Response;
import com.alura.forohub.domain.port.ResponseRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaResponseRepository extends JpaRepository<Response, Long>, ResponseRepository {
    List<Response> findByTopicId(Long topicId);
    
    @Query("SELECT r FROM Response r JOIN FETCH r.author WHERE r.topic.id = :topicId")
    List<Response> findByTopicIdWithAuthor(@Param("topicId") Long topicId);
    
    void deleteByTopicId(Long topicId);
}
