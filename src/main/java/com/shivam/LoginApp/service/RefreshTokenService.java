package com.shivam.LoginApp.service;

import com.shivam.LoginApp.exception.TokenRefreshException;
import com.shivam.LoginApp.model.RefreshToken;
import com.shivam.LoginApp.repository.RefreshTokenRepository;
import com.shivam.LoginApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenService {
 @Value("${app.jwt.jwtRefreshExpirationMs}")
 private Long refreshTokenDurationMs;

 @Autowired
 private RefreshTokenRepository refreshTokenRepository;

 @Autowired
 private UserRepository userRepository;

 public Optional<RefreshToken> findByToken(String token) {
     return refreshTokenRepository.findByToken(token);
 }

 public RefreshToken createRefreshToken(Long userId) {
     RefreshToken refreshToken = new RefreshToken();

     refreshToken.setUser(userRepository.findById(userId).get());
     refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs));
     refreshToken.setToken(UUID.randomUUID().toString());

     refreshToken = refreshTokenRepository.save(refreshToken);
     return refreshToken;
 }

 public RefreshToken verifyExpiration(RefreshToken token) {
     if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
         refreshTokenRepository.delete(token);
         throw new TokenRefreshException(token.getToken(), "Refresh token was expired. Please make a new sign in request");
     }

     return token;
 }

 @Transactional
 public int deleteByUserId(Long userId) {
     return refreshTokenRepository.deleteByUser(userRepository.findById(userId).get());
 }
}