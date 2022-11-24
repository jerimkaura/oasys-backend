package com.jerimkaura.oasis.web.api.models.requests;

import lombok.Data;

@Data
public class AuthRequest {
    private String username;
    private String password;
}
