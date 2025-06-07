package com.chargemate.controller;

import com.chargemate.dto.UserRegistrationDTO;
import com.chargemate.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.HashMap;
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
        try {
            dto.setUserType("STATION_OPERATOR");
            userService.registerUser(dto);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Station Operator registered successfully");
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            Map<String, String> response = new HashMap<>();
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
            errors.put(error.getField(), error.getDefaultMessage()));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }
}