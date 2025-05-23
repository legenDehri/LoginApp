package com.shivam.LoginApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shivam.LoginApp.dto.JwtResponse;
import com.shivam.LoginApp.dto.LoginRequest;
import com.shivam.LoginApp.dto.RegisterRequest;
import com.shivam.LoginApp.dto.TokenRefreshRequest;
import com.shivam.LoginApp.dto.TokenRefreshResponse;
import com.shivam.LoginApp.exception.TokenRefreshException;
import com.shivam.LoginApp.model.RefreshToken;
import com.shivam.LoginApp.model.User;
import com.shivam.LoginApp.security.JwtUtils;
import com.shivam.LoginApp.service.RefreshTokenService;
import com.shivam.LoginApp.service.UserService;

import jakarta.validation.Valid;

/**
 * Controller for handling authentication requests.
 * Provides endpoints for user registration and login.
 */
@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserService userService;

    @Autowired
    JwtUtils jwtUtils;
    
    @Autowired
    private RefreshTokenService refreshTokenService;

    /**
     * Register a new user
     * 
     * @param registerRequest registration details
     * @return response with success message
     */
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterRequest registerRequest) {
        // Create new user from request data
        User user = new User(
                registerRequest.getUsername(),
                registerRequest.getPassword(),
                registerRequest.getName(),
                registerRequest.getAge()
        );

        // Register user and return success message
        userService.registerUser(user);
        return ResponseEntity.ok("User registered successfully!");
    }

    /**
     * Authenticate a user and generate JWT token
     * 
     * @param loginRequest login credentials
     * @return response with JWT token and user details
     */
    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        // Authenticate user
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        // Set authentication in security context
        SecurityContextHolder.getContext().setAuthentication(authentication);
        
        // Generate JWT token
        String jwt = jwtUtils.generateJwtToken(authentication);
        
        // Get user details
        User user = userService.findByUsername(loginRequest.getUsername()).orElseThrow();
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user.getId());
        // Return JWT token and user details
        return ResponseEntity.ok(new JwtResponse(
                jwt,
                refreshToken.getToken(),
                user.getId(),
                user.getUsername(),
                user.getName(),
                user.getAge()));
    }
    
    @PostMapping("/refreshtoken")
    public ResponseEntity<?> refreshToken(@Valid @RequestBody TokenRefreshRequest request) {
        String requestRefreshToken = request.getRefreshToken();

        return refreshTokenService.findByToken(requestRefreshToken)
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    String token = jwtUtils.generateTokenFromUsername(user.getUsername());
                    return ResponseEntity.ok(new TokenRefreshResponse(token, requestRefreshToken));
                })
                .orElseThrow(() -> new TokenRefreshException(requestRefreshToken, "Refresh token is not in database!"));
    }
}