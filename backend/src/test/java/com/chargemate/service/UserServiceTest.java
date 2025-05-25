package com.chargemate.service;

import com.chargemate.dto.UserRegistrationDTO;
import com.chargemate.model.User;
import com.chargemate.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import app.getxray.junit.Requirement;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    private UserRegistrationDTO evDriverDTO;

    @BeforeEach
    void setUp() {
        evDriverDTO = new UserRegistrationDTO();
        evDriverDTO.setEmail("driver@example.com");
        evDriverDTO.setPassword("password123");
        evDriverDTO.setName("John Driver");
        evDriverDTO.setUserType("EV_DRIVER");
    }

    @Test
    @Requirement("CMATE-63") 
    void registerEVDriver_ShouldSaveUser() {
        when(passwordEncoder.encode(any())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenAnswer(i -> i.getArgument(0));

        User savedUser = userService.registerUser(evDriverDTO);

        assertNotNull(savedUser);
        assertEquals(evDriverDTO.getEmail(), savedUser.getEmail());
        assertEquals("encodedPassword", savedUser.getPassword());
        assertEquals(evDriverDTO.getName(), savedUser.getName());
        assertEquals("EV_DRIVER", savedUser.getUserType().toString());
        
        verify(userRepository).save(any(User.class));
    }

    @Test
    @Requirement("CMATE-63") 
    void registerUser_WithExistingEmail_ShouldThrowException() {
        when(userRepository.existsByEmail(evDriverDTO.getEmail())).thenReturn(true);

        assertThrows(RuntimeException.class, () -> userService.registerUser(evDriverDTO));
    }
} 