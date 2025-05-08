package org.example.railwayapp.dto;

import lombok.Getter;
import lombok.Setter;

// DTO для ответа /api/auth/login
@Getter
@Setter
public class LoginResponse {
    private boolean success;
    private String message;
    private String role; // Добавим роль пользователя в ответ
}