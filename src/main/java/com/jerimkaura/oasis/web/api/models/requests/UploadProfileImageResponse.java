package com.jerimkaura.oasis.web.api.models.requests;

import lombok.Data;

@Data
public class UploadProfileImageResponse {
    private Integer id;
    private String profileImageUrl;
}
