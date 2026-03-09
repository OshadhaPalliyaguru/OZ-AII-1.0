package com.ozprime.ai_assistant.service;

import com.ozprime.ai_assistant.model.entity.ChatMessage;
import com.ozprime.ai_assistant.model.entity.ChatSession;

import java.util.List;

public interface ChatService {
    ChatSession createChatSession(Long userId, String title);

    List<ChatSession> getUserSessions(Long userId);

    List<ChatMessage> getSessionMessages(Long sessionId);

    ChatMessage sendMessageAndGetAIResponse(Long sessionId, String userMessage);
}
