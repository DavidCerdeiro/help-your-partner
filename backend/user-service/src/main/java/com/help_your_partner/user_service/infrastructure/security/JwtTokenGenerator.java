package com.help_your_partner.user_service.infrastructure.security;

import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.help_your_partner.user_service.application.port.out.TokenGenerator;
import com.help_your_partner.user_service.domain.model.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;

@Component
public class JwtTokenGenerator implements TokenGenerator {
    @Value("${jwt.secret}") 
    private String secret;

    @Value("${jwt.expirationMs}")
    private long expirationMs;

    private Key signingKey;
    
    @PostConstruct
    public void init() {
        byte[] keyBytes = Decoders.BASE64.decode(this.secret);

        this.signingKey = Keys.hmacShaKeyFor(keyBytes);
    
    }
    @Override
    public String generateToken(User user) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expirationMs);

        // 1. Create the JWT Claims
        Claims claims = Jwts.claims().setSubject(user.getId().toString());

        claims.put("communityId", user.getCommunityId());
        claims.put("email", user.getEmail());

        // 3. Build the token
        return Jwts.builder()
                .setClaims(claims)                // 1. Add the payload (the claims)
                .setIssuedAt(now)                 // 2. Set the issued at date
                .setExpiration(expiryDate)        // 3. Set the expiration date
                .signWith(signingKey, SignatureAlgorithm.HS256) // 4. Sign the token with your key
                .compact();                       // 5. Convert it to a string
    }
 

    @Override
    public boolean validateToken(String token) {
        try {
            // 1. Prepare the token parser
            // 2. Tell it which signing key to use for validation
            // 3. Attempt to parse the token.
            Jwts.parserBuilder()
                .setSigningKey(signingKey) // Use the same key generated in init()
                .build()
                .parseClaimsJws(token); // This line is the one that validates

            // 4. If .parseClaimsJws() does not throw an exception, the token is valid.
            return true;

        } catch (Exception ex) {
            return false;
        }
        
        
        
    }

}
