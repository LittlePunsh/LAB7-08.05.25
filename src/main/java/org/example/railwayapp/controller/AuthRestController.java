package org.example.railwayapp.controller;

import org.example.railwayapp.dto.LoginRequest;
import org.example.railwayapp.dto.LoginResponse;
import org.example.railwayapp.dto.RegisterRequest;
import org.example.railwayapp.dto.RegisterResponse;
import org.example.railwayapp.model.users.User;
import org.example.railwayapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthRestController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> apiLogin(@RequestBody LoginRequest loginRequest) {
        LoginResponse response = new LoginResponse();

        Optional<User> userOptional = userService.findByUsername(loginRequest.getUsername());
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
                response.setSuccess(true);
                response.setMessage("Login successful (API check)");
                response.setRole(user.getRole());
                return ResponseEntity.ok(response);
            }
        }
        // Если пользователь не найден или пароль неверный
        response.setSuccess(false);
        response.setMessage("Invalid username or password");
        response.setRole(null);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }


    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> apiRegister(@RequestBody RegisterRequest registerRequest) {
        RegisterResponse response = new RegisterResponse();
        try {
            if (registerRequest.getUsername() == null || registerRequest.getUsername().isEmpty() ||
                    registerRequest.getPassword() == null || registerRequest.getPassword().isEmpty() ||
                    registerRequest.getEmail() == null || registerRequest.getEmail().isEmpty()) {
                response.setSuccess(false);
                response.setMessage("Username, password, and email are required");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            userService.registerUser(registerRequest.getUsername(), registerRequest.getPassword(), registerRequest.getEmail());
            response.setSuccess(true);
            response.setMessage("Registration successful");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.setSuccess(false);
            response.setMessage("Registration failed: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

}