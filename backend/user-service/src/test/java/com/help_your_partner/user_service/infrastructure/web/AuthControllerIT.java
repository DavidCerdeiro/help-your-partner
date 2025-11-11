package com.help_your_partner.user_service.infrastructure.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

// Importaciones estáticas para los métodos de MockMvc
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

// Importaciones de Mockito
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

// Jackson para convertir objetos a JSON
import com.fasterxml.jackson.databind.ObjectMapper;

// Tus DTOs y Casos de Uso
import com.help_your_partner.user_service.application.dto.LoginUserCommand;
import com.help_your_partner.user_service.application.dto.LoginUserResponse;
import com.help_your_partner.user_service.application.dto.RegisterUserCommand;
import com.help_your_partner.user_service.application.dto.RegisterUserResponse;
import com.help_your_partner.user_service.application.port.in.LoginUserUseCase;
import com.help_your_partner.user_service.application.port.in.RegisterUserUseCase;
import com.help_your_partner.user_service.infrastructure.config.SecurityConfig;
import com.help_your_partner.user_service.infrastructure.web.dto.LoginUserRequest;
import com.help_your_partner.user_service.infrastructure.web.dto.RegisterUserRequest;

/**
 * Prueba de integración para la capa Web (Controlador).
 */
@WebMvcTest(AuthController.class) 
@Import(SecurityConfig.class)
public class AuthControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @SuppressWarnings("removal")
    @MockBean
    private RegisterUserUseCase registerUserUseCase;

    @SuppressWarnings("removal")
    @MockBean
    private LoginUserUseCase loginUserUseCase;

    @Test
    void testRegisterUserSuccessful() throws Exception {
        // ARRANGE
        RegisterUserRequest request = new RegisterUserRequest("Homer", "Simpson", "homer@simpson.com", "donutsRul3", "Springfield Community", null);

        RegisterUserResponse useCaseResponse = new RegisterUserResponse("fake.jwt.token.register");
        
        // Simulate successful registration
        when(registerUserUseCase.registerUser(any(RegisterUserCommand.class)))
            .thenReturn(useCaseResponse);

        // ACT & ASSERT
        mockMvc.perform(
                post("/register") 
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
            )
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.token").value("fake.jwt.token.register"));
    }

    @Test
    void testLoginUserSuccessful() throws Exception {
        // ARRANGE
        LoginUserRequest request = new LoginUserRequest("test@example.com", "password123");

        LoginUserResponse useCaseResponse = new LoginUserResponse("fake.jwt.token.login");
        
        // Simulate successful login
        when(loginUserUseCase.loginUser(any(LoginUserCommand.class)))
            .thenReturn(useCaseResponse);

        // ACT & ASSERT
        mockMvc.perform(
                post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
            )
            .andExpect(status().isOk()) 
            .andExpect(jsonPath("$.token").value("fake.jwt.token.login")); 
    }

    @Test
    void testRegisterUserBadRequest() throws Exception {
        // ARRANGE
        RegisterUserRequest request = new RegisterUserRequest("Homer", "Simpson", "homer@simpson.com", "donutsRul3", "Springfield Community", null);

        // Failed registration simulation
        when(registerUserUseCase.registerUser(any(RegisterUserCommand.class)))
            .thenReturn(null);

        // ACT & ASSERT
        mockMvc.perform(
                post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
            )
            .andExpect(status().isBadRequest()); 
    }

}