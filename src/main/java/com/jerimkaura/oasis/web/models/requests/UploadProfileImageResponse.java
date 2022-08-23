package com.jerimkaura.oasis.web.models.requests;

import lombok.Data;

@Data
public class UploadProfileImageResponse {
    private Integer id;
    private String profileImageUrl;
}
