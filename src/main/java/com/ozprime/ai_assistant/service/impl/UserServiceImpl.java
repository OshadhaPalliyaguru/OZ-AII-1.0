package com.ozprime.ai_assistant.service.impl;

import com.ozprime.ai_assistant.model.entity.User;
import com.ozprime.ai_assistant.repository.UserRepository;
import com.ozprime.ai_assistant.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;


    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User registerUser(String username, String email, String password) {

        if (userRepository.existsByEmail(email)) {
            throw new RuntimeException("Email is already registered!");
        }

        User user = new User();
        user.setUsername(username);
        user.setEmail(email);


        user.setPasswordHash(password);

        return userRepository.save(user);
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));
    }
}
