package com.chargemate.service;

import com.chargemate.dto.AuthResponse;
import com.chargemate.dto.LoginRequestDTO;
import com.chargemate.exception.UnauthorizedException;
import com.chargemate.model.User;
import com.chargemate.model.UserType;
import com.chargemate.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public AuthResponse login(LoginRequestDTO loginRequest) {
        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new UnauthorizedException("Invalid credentials"));

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new UnauthorizedException("Invalid credentials");
        }

        if (user.getUserType() != UserType.EV_DRIVER) {
            throw new UnauthorizedException("User is not an EV Driver");
        }

        String token = jwtService.generateToken(user.getEmail(), user.getId(), user.getUserType().name());

        return new AuthResponse(token, user.getId(), user.getEmail(), user.getUserType().name());
    }
} 