package org.example.railwayapp.dto;

import lombok.Getter;
import lombok.Setter;

// DTO для тела запроса /api/auth/register
@Getter
@Setter
public class RegisterRequest {
    private String username;
    private String password;
    private String email;
}