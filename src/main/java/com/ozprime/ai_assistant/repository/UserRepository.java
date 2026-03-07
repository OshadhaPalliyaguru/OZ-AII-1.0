package com.ozprime.ai_assistant.repository;

import com.ozprime.ai_assistant.model.entity.User;

import java.util.Optional;

public interface UserRepository {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
}
