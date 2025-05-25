package com.chargemate.controller;

import com.chargemate.dto.UserRegistrationDTO;
import com.chargemate.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
public class UserRegistrationController {

    @Autowired
    private UserService userService;

    @PostMapping("/register/ev-driver")
    public ResponseEntity<Map<String, String>> registerEVDriver(@RequestBody UserRegistrationDTO registrationDTO) {
        // Ainda não implementado
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }
}