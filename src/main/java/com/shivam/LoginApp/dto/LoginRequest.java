package com.shivam.LoginApp.dto;

/**
 * Data Transfer Object for login requests.
 * Contains user credentials for authentication.
 *  This class is used to transfer data between client and server.
 *  It contains the username and password fields.
 */
public class LoginRequest {
    
    
    private String username;

    
    private String password;

    // Getters and Setters
    
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}