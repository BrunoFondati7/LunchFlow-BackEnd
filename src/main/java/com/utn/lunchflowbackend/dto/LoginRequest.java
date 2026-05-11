package com.utn.lunchflowbackend.dto;

import lombok.Data;

@Data // Esto genera getters, setters, toString, etc. automáticamente
public class LoginRequest {
    private String legajo;
    private String password;
}