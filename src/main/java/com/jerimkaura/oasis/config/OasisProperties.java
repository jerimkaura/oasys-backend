package com.jerimkaura.oasis.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("oasis")
public record OasisProperties(String awsId, String awsSecret, String awsBucketName, String awsRegion) {
}
