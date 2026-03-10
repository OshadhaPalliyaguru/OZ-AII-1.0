package com.ozprime.ai_assistant.service.impl;

import com.ozprime.ai_assistant.enums.Role;
import com.ozprime.ai_assistant.model.entity.ChatMessage;
import com.ozprime.ai_assistant.model.entity.ChatSession;
import com.ozprime.ai_assistant.model.entity.User;
import com.ozprime.ai_assistant.repository.ChatMessageRepository;
import com.ozprime.ai_assistant.repository.ChatSessionRepository;
import com.ozprime.ai_assistant.repository.UserRepository;
import com.ozprime.ai_assistant.service.ChatService;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

@Service
public class ChatServiceImpl implements ChatService {
    private final ChatSessionRepository sessionRepository;
    private final ChatMessageRepository messageRepository;
    private final UserRepository userRepository;

    private final ChatClient chatClient;


    public ChatServiceImpl(ChatSessionRepository sessionRepository,
                           ChatMessageRepository messageRepository,
                           UserRepository userRepository,
                           ChatClient.Builder chatClientBuilder) {
        this.sessionRepository = sessionRepository;
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
        this.chatClient = chatClientBuilder.build();
    }

    @Override
    public ChatSession createChatSession(Long userId, String title) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        ChatSession session = new ChatSession();
        session.setUser(user);
        session.setTitle(title);
        return sessionRepository.save(session);
    }

    @Override
    public List<ChatSession> getUserSessions(Long userId) {
        return sessionRepository.findByUserIdOrderByCreatedAtDesc(userId);
    }

    @Override
    public List<ChatMessage> getSessionMessages(Long sessionId) {
        return messageRepository.findBySessionIdOrderByTimestampAsc(sessionId);
    }

    @Override
    @Transactional
    public ChatMessage sendMessageAndGetAIResponse(Long sessionId, String userMessageContent) {
        ChatSession session = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new RuntimeException("Session not found"));


        ChatMessage userMessage = new ChatMessage();
        userMessage.setSession(session);
        userMessage.setRole(Role.USER);
        userMessage.setContent(userMessageContent);
        messageRepository.save(userMessage);


        String aiResponseText = callExternalAI(userMessageContent);


        ChatMessage aiMessage = new ChatMessage();
        aiMessage.setSession(session);
        aiMessage.setRole(Role.AI);
        aiMessage.setContent(aiResponseText);

        return messageRepository.save(aiMessage);
    }


    private String callExternalAI(String prompt) {
        return chatClient.prompt()
                .user(prompt)
                .call()
                .content();
    }
}
