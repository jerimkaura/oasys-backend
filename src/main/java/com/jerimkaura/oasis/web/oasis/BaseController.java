package com.jerimkaura.oasis.web.oasis;

import com.jerimkaura.oasis.web.BaseResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BaseController {
    @GetMapping("/test")
    ResponseEntity<BaseResponse<?>> getIndexPage() {
        return new ResponseEntity<>(
                new BaseResponse<>(
                        "Success",
                        HttpStatus.OK.value(),
                        "This is the index page"
                ),
                HttpStatus.OK
        );
    }
}
