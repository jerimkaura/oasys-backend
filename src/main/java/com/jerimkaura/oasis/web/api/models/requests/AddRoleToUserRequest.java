package com.jerimkaura.oasis.web.api.models.requests;

import lombok.Data;

@Data
public class AddRoleToUserRequest {
    private String username;
    private String roleName;
}
