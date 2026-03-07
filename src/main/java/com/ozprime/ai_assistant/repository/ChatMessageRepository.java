package com.ozprime.ai_assistant.repository;

import com.ozprime.ai_assistant.model.entity.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage,Long> {

    List<ChatMessage> findBySessionIdOrderByTimestampAsc(Long sessionID);
}
