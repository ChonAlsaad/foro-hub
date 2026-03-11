package com.alura.forohub.domain.port;

import com.alura.forohub.domain.model.Response;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface ResponseRepository {
    Response save(Response response);
    List<Response> findByTopicId(Long topicId);
    
    @Query("SELECT r FROM Response r JOIN FETCH r.author WHERE r.topic.id = :topicId")
    List<Response> findByTopicIdWithAuthor(@Param("topicId") Long topicId);
    
    void deleteByTopicId(Long topicId);
}
