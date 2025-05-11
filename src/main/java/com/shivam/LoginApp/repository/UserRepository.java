package com.shivam.LoginApp.repository;

import com.shivam.LoginApp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for User entity.
 * Provides methods to interact with the user database.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    /**
     * Find a user by username
     * 
     * @param username the username to search for
     * @return Optional containing User if found, empty Optional otherwise
     */
    Optional<User> findByUsername(String username);
    
    /**
     * Check if a user with the given username exists
     * 
     * @param username the username to check
     * @return true if user exists, false otherwise
     */
    Boolean existsByUsername(String username);
}