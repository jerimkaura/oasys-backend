package com.jerimkaura.oasis.web.api;

import com.jerimkaura.oasis.domain.Church;
import com.jerimkaura.oasis.domain.User;
import com.jerimkaura.oasis.repository.ChurchRepository;
import com.jerimkaura.oasis.service.church.ChurchService;
import com.jerimkaura.oasis.service.user.UserService;
import com.jerimkaura.oasis.web.BaseResponse;
import com.jerimkaura.oasis.web.api.models.dto.ChurchDto;
import com.jerimkaura.oasis.web.api.models.dto.UserDto;
import com.jerimkaura.oasis.web.api.models.requests.CreateChurchRequest;
import com.jerimkaura.oasis.web.api.models.requests.EnrollUserToChurchRequest;
import com.jerimkaura.oasis.web.api.models.requests.UpdateChurchRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Locale;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/")
@Slf4j
public class ChurchController {
    private final ChurchService churchService;
    private final UserService userService;
    private final ChurchRepository churchRepository;

    @PostMapping("/churches")
    public ResponseEntity<BaseResponse<?>> createChurch(@RequestBody CreateChurchRequest createChurchRequest) {
        Church church = new Church(
                null,
                createChurchRequest.getName(),
                createChurchRequest.getLocation(),
                new HashSet<>()
        );
        log.error(church.toString());
        return new ResponseEntity<>(
                new BaseResponse<>(
                        "Success",
                        HttpStatus.OK.value(),
                        new ModelMapper().map(churchService.saveChurch(church), ChurchDto.class)
                ),
                HttpStatus.OK
        );
    }

    @GetMapping("/churches")
    public ResponseEntity<BaseResponse<?>> getChurches() {
        return new ResponseEntity<>(
                new BaseResponse<>(
                        "Success",
                        HttpStatus.OK.value(),
                        churchService.getChurches().stream().map(church -> new ModelMapper().map(church, ChurchDto.class))
                ),
                HttpStatus.OK
        );
    }

    @GetMapping("/churches/{id}")
    public ResponseEntity<BaseResponse<?>> getChurchesDetails(@PathVariable Long id) {
        Church church = churchRepository.findChurchById(id);
        if (church != null) {
            return new ResponseEntity<>(
                    new BaseResponse<>(
                            "Success",
                            HttpStatus.OK.value(),
                            new ModelMapper().map(churchService.getChurchById(id), ChurchDto.class)
                    ),
                    HttpStatus.OK
            );
        } else {
            return new ResponseEntity<>(
                    new BaseResponse<>(
                            "Success",
                            HttpStatus.OK.value(),
                            "No Church found with the supplied ID"
                    ),
                    HttpStatus.OK
            );
        }

    }

    @PatchMapping("/churches/")
    public ResponseEntity<BaseResponse<?>> updateChurch(@RequestBody UpdateChurchRequest updateChurchRequest) {
        Church church = churchService.getChurchById(updateChurchRequest.getId());
        if (church != null) {
            return new ResponseEntity<>(
                    new BaseResponse<>(
                            "Success",
                            HttpStatus.OK.value(),
                            new ModelMapper().map(churchService.updateChurch(updateChurchRequest), ChurchDto.class)
                    ),
                    HttpStatus.OK
            );
        } else {
            return new ResponseEntity<>(
                    new BaseResponse<>(
                            "Success",
                            HttpStatus.OK.value(),
                            "No Church found with the supplied ID"
                    ),
                    HttpStatus.OK
            );
        }

    }

    @GetMapping("/churches/{id}/users")
    public ResponseEntity<BaseResponse<?>> getUsersByChurch(@PathVariable Long id) {
        Church church = churchService.getChurchById(id);
        if (church != null) {
            return new ResponseEntity<>(
                    new BaseResponse<>(
                            "Success",
                            HttpStatus.OK.value(),
                            userService.getUsersByChurch(church).stream().map(user -> new ModelMapper().map(user, UserDto.class))
                    ),
                    HttpStatus.OK
            );
        } else {
            return new ResponseEntity<>(
                    new BaseResponse<>(
                            "Success",
                            HttpStatus.OK.value(),
                            "No Church found with the supplied ID"
                    ),
                    HttpStatus.OK
            );
        }

    }

    @PostMapping("churches/{id}/enroll")
    public ResponseEntity<BaseResponse<?>> enrollUserToChurch(@PathVariable Long id, @RequestBody EnrollUserToChurchRequest enrollUserToChurchRequest) {
        Church church = churchService.getChurchById(id);
        Long userId = enrollUserToChurchRequest.getUserId();
        log.error(String.valueOf(enrollUserToChurchRequest));
        if (church != null) {
            User user = userService.getUserById(userId);
            if (user != null) {
                churchService.enrollUserToChurch(user, id);
                return new ResponseEntity<>(
                        new BaseResponse<>(
                                "Success",
                                HttpStatus.OK.value(),
                                new ModelMapper().map(user, UserDto.class)
                        ),
                        HttpStatus.OK
                );
            }else{
                return new ResponseEntity<>(
                        new BaseResponse<>(
                                "Success",
                                HttpStatus.OK.value(),
                                "No User found with the supplied ID"
                        ),
                        HttpStatus.OK
                );
            }

        } else {
            return new ResponseEntity<>(
                    new BaseResponse<>(
                            "Success",
                            HttpStatus.OK.value(),
                            "No Church found with the supplied ID"
                    ),
                    HttpStatus.OK
            );
        }

    }
}
