package com.chargemate.controller;

import com.chargemate.dto.UserRegistrationDTO;
import com.chargemate.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth/register")
public class StationOperatorRegistrationController {

    private final UserService userService;

    @Autowired
    public StationOperatorRegistrationController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/station-operator")
    public ResponseEntity<Map<String, String>> registerStationOperator(@Valid @RequestBody UserRegistrationDTO dto) {
        dto.setUserType("STATION_OPERATOR");
        userService.registerUser(dto);
        return ResponseEntity.status(201).body(Map.of("message", "Station Operator registered successfully"));
    }
}