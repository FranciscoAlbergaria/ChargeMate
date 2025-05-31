package com.chargemate.controller;

import com.chargemate.dto.UserRegistrationDTO;
import com.chargemate.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
public class UserRegistrationController {

    @Autowired
    private UserService userService;

    @PostMapping("/register/ev-driver")
    public ResponseEntity<Map<String, String>> registerEVDriver(@Valid @RequestBody UserRegistrationDTO registrationDTO) {
        try {
            registrationDTO.setUserType("EV_DRIVER");
            userService.registerUser(registrationDTO);
            Map<String, String> response = new HashMap<>();
            response.put("message", "EV Driver registered successfully");
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            Map<String, String> response = new HashMap<>();
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
}