package com.help_your_partner.user_service.application.service;

import org.springframework.stereotype.Service;

import com.help_your_partner.user_service.application.dto.LoginUserCommand;
import com.help_your_partner.user_service.application.dto.LoginUserResponse;
import com.help_your_partner.user_service.application.dto.RegisterUserCommand;
import com.help_your_partner.user_service.application.dto.RegisterUserResponse;
import com.help_your_partner.user_service.application.port.in.LoginUserUseCase;
import com.help_your_partner.user_service.application.port.in.RegisterUserUseCase;
import com.help_your_partner.user_service.application.port.out.PasswordHasher;
import com.help_your_partner.user_service.application.port.out.TokenGenerator;
import com.help_your_partner.user_service.domain.model.Community;
import com.help_your_partner.user_service.domain.model.User;
import com.help_your_partner.user_service.domain.port.out.UserRepository;

@Service
public class AuthService implements RegisterUserUseCase, LoginUserUseCase{

    private final PasswordHasher passwordHasher;
    private final TokenGenerator tokenGenerator;
    private final UserRepository userRepository;
    private final CommunityService communityService;

    public AuthService(PasswordHasher passwordHasher, TokenGenerator tokenGenerator,
            UserRepository userRepository, CommunityService communityService) {
        this.passwordHasher = passwordHasher;
        this.tokenGenerator = tokenGenerator;
        this.userRepository = userRepository;
        this.communityService = communityService;
    }

    @Override
    public LoginUserResponse loginUser(LoginUserCommand request) {
        User user = userRepository.findByEmail(request.getEmail());
        // Validate that user exists and password matches
        if(user == null || !passwordHasher.match(request.getPassword(), user.getPasswordHash()))
            throw new IllegalArgumentException("Invalid email or password");
        return new LoginUserResponse(tokenGenerator.generateToken(user));
    }

    @Override
    public RegisterUserResponse registerUser(RegisterUserCommand request) {
        // Validate that do not exist user with same email
        if(userRepository.findByEmail(request.getEmail()) != null)
            throw new IllegalArgumentException("Email already in use");

        String hashedPassword = passwordHasher.hash(request.getPassword());
        Community community;

        if(request.getNewCommunityName() != null) {
            community = communityService.createCommunity(request.getNewCommunityName());
        }else{
            community = communityService.findByCode(request.getExistingCommunityCode());
        }

        User newUser = new User(null, request.getName(), request.getSurname(), request.getEmail(), hashedPassword, community.getId());
        userRepository.save(newUser);
        return new RegisterUserResponse(tokenGenerator.generateToken(newUser));
    }
    
}
