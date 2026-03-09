package com.ozprime.ai_assistant.controller;


import com.ozprime.ai_assistant.model.entity.ChatMessage;
import com.ozprime.ai_assistant.model.entity.ChatSession;
import com.ozprime.ai_assistant.service.ChatService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chat")

public class ChatController {
    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }


    public record SessionRequest(Long userId, String title) {}
    public record MessageRequest(Long sessionId, String content) {}


    @PostMapping("/sessions")
    public ResponseEntity<ChatSession> createSession(@RequestBody SessionRequest request) {
        ChatSession session = chatService.createChatSession(request.userId(), request.title());
        return ResponseEntity.ok(session);
    }


    @GetMapping("/users/{userId}/sessions")
    public ResponseEntity<List<ChatSession>> getUserSessions(@PathVariable Long userId) {
        return ResponseEntity.ok(chatService.getUserSessions(userId));
    }


    @PostMapping("/send")
    public ResponseEntity<ChatMessage> sendMessage(@RequestBody MessageRequest request) {
        ChatMessage aiResponse = chatService.sendMessageAndGetAIResponse(request.sessionId(), request.content());
        return ResponseEntity.ok(aiResponse);
    }


    @GetMapping("/sessions/{sessionId}/messages")
    public ResponseEntity<List<ChatMessage>> getSessionMessages(@PathVariable Long sessionId) {
        return ResponseEntity.ok(chatService.getSessionMessages(sessionId));
    }
}
