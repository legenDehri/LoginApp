package com.shivam.LoginApp.security;

import com.shivam.LoginApp.model.User;
import com.shivam.LoginApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

/**
 * Service class for loading user details from the database.
 * Implements Spring Security's UserDetailsService.
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    /**
     * Load user details by username
     * This method is called by Spring Security during authentication.
     * It retrieves user details from the database and creates a UserDetails object.
     * 
     * @param username the username to search for
     * @return UserDetails object containing user information
     * @throws UsernameNotFoundException if user not found
     */
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        // Create Spring Security UserDetails object
        // This object contains user information and authorities (roles)
        // The User object is used to create a UserDetails object
        // Using empty authorities list as we're not implementing role-based authorization in this example
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                new ArrayList<>()
        );
    }
}