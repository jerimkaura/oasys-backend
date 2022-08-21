package com.jerimkaura.oasis.web;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
public class BaseResponse<T> {
    private String message;
    private Integer statusCode;
    private T data;
}
