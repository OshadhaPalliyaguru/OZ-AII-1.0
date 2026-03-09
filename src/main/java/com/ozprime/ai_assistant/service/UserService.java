package com.ozprime.ai_assistant.service;

import com.ozprime.ai_assistant.model.entity.User;

public interface UserService {
    User registerUser(String username, String email, String password);
    User getUserByEmail(String email);
}
