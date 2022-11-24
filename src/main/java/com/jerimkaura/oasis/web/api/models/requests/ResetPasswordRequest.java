package com.jerimkaura.oasis.web.api.models.requests;

import lombok.Data;

@Data
public class ResetPasswordRequest {
    private String token;
    private String password;
}
