package com.jerimkaura.oasis.config;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum BucketNames {
    PROFILES_BUCKET_NAME("oasis-dawn");
    private final String bucketName;

}
