package com.ozprime.ai_assistant.controller;

import com.ozprime.ai_assistant.model.entity.User;
import com.ozprime.ai_assistant.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    public record RegisterRequest(String username, String email, String password) {}

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody RegisterRequest request) {
        User newUser = userService.registerUser(request.username(), request.email(), request.password());
        return ResponseEntity.ok(newUser);
    }
}
