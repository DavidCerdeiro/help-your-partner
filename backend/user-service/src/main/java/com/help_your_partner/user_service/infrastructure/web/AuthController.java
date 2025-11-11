package com.help_your_partner.user_service.infrastructure.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.help_your_partner.user_service.application.dto.LoginUserCommand;
import com.help_your_partner.user_service.application.dto.LoginUserResponse;
import com.help_your_partner.user_service.application.dto.RegisterUserCommand;
import com.help_your_partner.user_service.application.dto.RegisterUserResponse;
import com.help_your_partner.user_service.application.port.in.LoginUserUseCase;
import com.help_your_partner.user_service.application.port.in.RegisterUserUseCase;
import com.help_your_partner.user_service.infrastructure.web.dto.LoginUserRequest;
import com.help_your_partner.user_service.infrastructure.web.dto.RegisterUserRequest;

@RestController
public class AuthController {
    private final RegisterUserUseCase registerUserUseCase;
    private final LoginUserUseCase loginUserUseCase;

    public AuthController(RegisterUserUseCase registerUserUseCase, LoginUserUseCase loginUserUseCase) {
        this.registerUserUseCase = registerUserUseCase;
        this.loginUserUseCase = loginUserUseCase;
    }

    /**
     * Endpoint for user registration
     */
    @PostMapping("/register")
    public ResponseEntity<RegisterUserResponse> registerUser(@RequestBody RegisterUserRequest request) {
        RegisterUserCommand command = request.toCommand();
        RegisterUserResponse response = registerUserUseCase.registerUser(command);

        if (response != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
    
    /**
     * Endpoint for user login
     */
    @PostMapping("/login")
    public ResponseEntity<LoginUserResponse> loginUser(@RequestBody LoginUserRequest request) {
        LoginUserCommand command = request.toCommand();
        LoginUserResponse response = loginUserUseCase.loginUser(command);
        
        if (response != null) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
}

