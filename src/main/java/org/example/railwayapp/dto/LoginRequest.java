package org.example.railwayapp.dto;

import lombok.Getter;
import lombok.Setter;

// DTO для тела запроса /api/auth/login
@Getter
@Setter
public class LoginRequest {
    private String username;
    private String password;
}