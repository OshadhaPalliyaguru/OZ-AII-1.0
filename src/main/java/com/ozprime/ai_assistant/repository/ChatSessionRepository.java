package com.ozprime.ai_assistant.repository;

import com.ozprime.ai_assistant.model.entity.ChatSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatSessionRepository extends JpaRepository<ChatSession,Long> {
    List<ChatSession> findByUserIdOrderByCreatedAtDesc(Long userId);
}
