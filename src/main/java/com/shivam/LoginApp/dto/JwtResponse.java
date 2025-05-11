package com.shivam.LoginApp.dto;

/**
 * Data Transfer Object for JWT authentication responses.
 * Contains JWT token and user information after successful authentication.
 */
public class JwtResponse {
    
    private String token;
    private String type = "Bearer";
    private Long id;
    private String username;
    private String name;
    private Integer age;

    public JwtResponse(String token, Long id, String username, String name, Integer age) {
        this.token = token;
        this.id = id;
        this.username = username;
        this.name = name;
        this.age = age;
    }

    // Getters and Setters
    
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}