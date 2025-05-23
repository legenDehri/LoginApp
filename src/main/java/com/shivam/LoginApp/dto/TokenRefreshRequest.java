package com.shivam.LoginApp.dto;

/**
* Data Transfer Object for token refresh requests. Contains the refresh token
* that is used to obtain a new access token.
*/

import jakarta.validation.constraints.NotBlank;

public class TokenRefreshRequest {
 @NotBlank
 private String refreshToken;

 public String getRefreshToken() {
     return refreshToken;
 }

 public void setRefreshToken(String refreshToken) {
     this.refreshToken = refreshToken;
 }
}
