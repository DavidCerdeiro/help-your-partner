package com.help_your_partner.task_service.infrastructure.security;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtils jwtUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request, 
                                    HttpServletResponse response, 
                                    FilterChain filterChain) throws ServletException, IOException {
        
        String token = extractTokenFromRequest(request);

        if (token != null && jwtUtils.validateToken(token)) {
            
            // 1. If the token is valid, extract claims
            Claims claims = jwtUtils.getClaims(token);
            
            // 2. Extract data from the token
            String userId = claims.getSubject();
            String communityId = claims.get("communityId", String.class);

            // 3. Establish authentication in the security context
            // Create an authentication object
            UsernamePasswordAuthenticationToken authentication = 
                    new UsernamePasswordAuthenticationToken(
                        userId,  
                        null,   
                        new ArrayList<>() // No roles/authorities for now
                    );
            
            // 4. Save the communityId in the authentication "details"
            authentication.setDetails(communityId); 

            // 5. "Log in" the user for this request
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        // 6. Continue with the rest of the filters (and finally, the controller)
        filterChain.doFilter(request, response);
    }

    // Utility method to extract the "Bearer <token>"
    private String extractTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7); // Returns only the token
        }
        return null;
    }
}