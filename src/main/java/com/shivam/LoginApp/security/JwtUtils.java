package com.shivam.LoginApp.security;

import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;

import java.util.Date;

/**
 * Utility class for JWT token generation and validation.
 * Handles the creation, parsing, and validation of JWT tokens.
 * Provides methods to extract user information from tokens.
 */
@Component
public class JwtUtils {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);
    
    @Autowired
    private JwtConfig jwtConfig;
    
    

    /**
     * Generate a JWT token from authentication details
     * 
     * @param authentication the authenticated user details
     * @return JWT token string
     */
    public String generateJwtToken(Authentication authentication) {
        UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();
        
        // Create proper SecretKey from the secret string
        // Use the secret key to sign the JWT token
        // The secret key should be at least 256 bits long for HS256
        SecretKey key = Keys.hmacShaKeyFor(jwtConfig.getSecret().getBytes());
        
        return Jwts.builder()
            .setSubject(userPrincipal.getUsername())
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + jwtConfig.getExpiration()))
            .signWith(key, SignatureAlgorithm.HS512)
            .compact();
    }

	/**
	 * Generate a JWT token from username
	 * 
	 * @param username the username to include in the token
	 * @return JWT token string
	 */
    
    public String generateTokenFromUsername(String username) {
    	SecretKey key = Keys.hmacShaKeyFor(jwtConfig.getSecret().getBytes());
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtConfig.getRefreshExpiration()))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    /**
     * Extract username from JWT token
     * 
     * @param token JWT token
     * @return username from token
     */
    public String getUsernameFromJwtToken(String token) {
        // Create proper SecretKey from the secret string
        SecretKey key = Keys.hmacShaKeyFor(jwtConfig.getSecret().getBytes());
        
        return Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token)
            .getBody()
            .getSubject();
    }

    /**
     * Validate JWT token
     * 
     * @param authToken JWT token to validate
     * @return true if valid, false otherwise
     */
    public boolean validateJwtToken(String authToken) {
        try {
            // Create proper SecretKey from the secret string
            SecretKey key = Keys.hmacShaKeyFor(jwtConfig.getSecret().getBytes());
            Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(authToken);
            return true;
        } catch (SecurityException e) {
            logger.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }

        return false;
    }
}