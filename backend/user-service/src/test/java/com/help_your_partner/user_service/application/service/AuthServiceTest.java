package com.help_your_partner.user_service.application.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.help_your_partner.user_service.application.dto.LoginUserCommand;
import com.help_your_partner.user_service.application.dto.LoginUserResponse;
import com.help_your_partner.user_service.application.dto.RegisterUserCommand;
import com.help_your_partner.user_service.application.dto.RegisterUserResponse;
import com.help_your_partner.user_service.application.port.out.PasswordHasher;
import com.help_your_partner.user_service.application.port.out.TokenGenerator;
import com.help_your_partner.user_service.domain.model.Community;
import com.help_your_partner.user_service.domain.model.User;
import com.help_your_partner.user_service.domain.port.out.UserRepository;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    // Mocks
    @Mock
    private PasswordHasher mockPasswordHasher;
    @Mock
    private TokenGenerator mockTokenGenerator;
    @Mock
    private UserRepository mockUserRepository;
    @Mock
    private CommunityService mockCommunityService;

    // SUT
    @InjectMocks
    private AuthService authService;

    @Test
    public void testRegisterUser() {
        // ARRANGE
        RegisterUserCommand command = new RegisterUserCommand(
            "John",
            "Doe",
            "john.doe@example.com",
            "password123",
            "University Community"
        );

        when(mockUserRepository.findByEmail("john.doe@example.com")).thenReturn(null);

        when(mockPasswordHasher.hash("password123")).thenReturn("hashed_password_xyz");
        
        Community simulatedCommunity = new Community("comm-id-123", "University Community", "CODE123");
        when(mockCommunityService.createCommunity("University Community")).thenReturn(simulatedCommunity);
        
        doNothing().when(mockUserRepository).save(any(User.class));

        when(mockTokenGenerator.generateToken(any(User.class))).thenReturn("fake.jwt.token");
        
        // ACT
        RegisterUserResponse response = authService.registerUser(command);
        
        // ASSERT
        assertNotNull(response);        
        
        verify(mockUserRepository, times(1)).findByEmail("john.doe@example.com");
        
        verify(mockPasswordHasher, times(1)).hash("password123");
        
        verify(mockCommunityService, times(1)).createCommunity("University Community");
        
        verify(mockCommunityService, never()).findByCode(anyString());
        
        verify(mockUserRepository, times(1)).save(any(User.class));
        
        verify(mockTokenGenerator, times(1)).generateToken(any(User.class));
    }

    @Test
    public void testLoginUser() {
        //ARRANGE
        LoginUserCommand command = new LoginUserCommand(
            "john.doe@example.com",
            "password123"
        );
        
        User simulatedUser = new User();
        simulatedUser.setEmail("john.doe@example.com");
        simulatedUser.setPasswordHash("hashed_password_xyz"); // Contraseña hasheada
        
        
        when(mockUserRepository.findByEmail("john.doe@example.com")).thenReturn(simulatedUser);
        
        when(mockPasswordHasher.match("password123", "hashed_password_xyz")).thenReturn(true);
        
        when(mockTokenGenerator.generateToken(simulatedUser)).thenReturn("fake.jwt.token.login");

        //ACT
        LoginUserResponse response = authService.loginUser(command);
        
        // ASSERT
        assertNotNull(response);
        assertEquals("fake.jwt.token.login", response.getToken());
        
        verify(mockUserRepository, times(1)).findByEmail("john.doe@example.com");
        verify(mockPasswordHasher, times(1)).match("password123", "hashed_password_xyz");
        verify(mockTokenGenerator, times(1)).generateToken(simulatedUser);
    }
}
