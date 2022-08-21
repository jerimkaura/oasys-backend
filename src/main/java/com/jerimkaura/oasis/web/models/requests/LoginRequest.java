package com.jerimkaura.oasis.web.models.requests;

import lombok.*;

@Data
public class LoginRequest {
    private String username;
    private String password;
}