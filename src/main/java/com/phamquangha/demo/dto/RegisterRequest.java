package com.phamquangha.demo.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {
    private String username;
    private String password;
    private String email;
    private String role; // Có thể là "ROLE_USER" hoặc "ROLE_ADMIN"
}
