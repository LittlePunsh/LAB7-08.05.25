package org.example.railwayapp.dto;

import lombok.Getter;
import lombok.Setter;

// DTO для ответа /api/auth/register
@Getter
@Setter
public class RegisterResponse {
    private boolean success;
    private String message;
}