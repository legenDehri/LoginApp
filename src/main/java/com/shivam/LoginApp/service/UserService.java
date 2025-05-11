package com.shivam.LoginApp.service;

import com.shivam.LoginApp.model.User;
import com.shivam.LoginApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service class for user-related operations.
 * Handles business logic for user management.
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    /**
     * Register a new user
     * 
     * @param user the user to register
     * @return the registered user
     * @throws RuntimeException if username already exists
     */
    public User registerUser(User user) {
        // Check if username already exists
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new RuntimeException("Username is already taken");
        }
        
        // Encode password
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        
        // Save user to database
        return userRepository.save(user);
    }
    
    /**
     * Find user by username
     * 
     * @param username the username to search for
     * @return Optional containing User if found
     */
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    
    /**
     * Find user by ID
     * 
     * @param id the user ID to search for
     * @return Optional containing User if found
     */
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }
}