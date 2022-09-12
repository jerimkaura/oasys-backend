package com.jerimkaura.oasis.web.api;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.jerimkaura.oasis.domain.Church;
import com.jerimkaura.oasis.domain.Role;
import com.jerimkaura.oasis.domain.User;
import com.jerimkaura.oasis.repository.UserRepository;
import com.jerimkaura.oasis.service.church.ChurchService;
import com.jerimkaura.oasis.service.user.UserService;
import com.jerimkaura.oasis.utils.EmailValidator;
import com.jerimkaura.oasis.utils.JwtUtils;
import com.jerimkaura.oasis.web.BaseResponse;
import com.jerimkaura.oasis.web.models.dto.UserDto;
import com.jerimkaura.oasis.web.models.requests.*;
import com.jerimkaura.oasis.web.models.responses.AuthResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
@Slf4j
public class UserController {
    private final UserService userService;
    private final ChurchService churchService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final EmailValidator emailValidator;
    private final UserRepository userRepository;


    @PostMapping("/register")
    public ResponseEntity<BaseResponse<?>> register(@RequestBody RegisterRequest registerRequest) {
        boolean isEmailValid = emailValidator.test(registerRequest.getEmail());
        if(!isEmailValid){
            throw new IllegalStateException("Email is not valid");
        }
        User user = new User(
                null,
                registerRequest.getFirstname(),
                registerRequest.getLastname(),
                registerRequest.getEmail(),
                null,
                false,
                null,
                null,
                registerRequest.getPassword(),
                new ArrayList<>(),

                null,
                new HashSet<>()
        );
        User userInDatabase = userRepository.findUserByUsername(user.getUsername());
        if (userInDatabase != null) {
            return new ResponseEntity<>(
                    new BaseResponse<>(
                            "Success",
                            HttpStatus.OK.value(),
                            "Email already taken"
                    ),
                    HttpStatus.OK
            );
        }else {
            return new ResponseEntity<>(
                    new BaseResponse<>(
                            "Success",
                            HttpStatus.CREATED.value(),
                            userService.saveUser(user)
                    ),
                    HttpStatus.CREATED
            );
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        try {
            UsernamePasswordAuthenticationToken authenticationToken
                    = new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword());
            Authentication authentication = authenticationManager.authenticate(authenticationToken);
            Map<String, String> tokens = jwtUtils.generateJWT(authentication);
            AuthResponse authResponse = new AuthResponse(tokens.get("access_token"), tokens.get("refresh_token"));
            return new ResponseEntity<>(
                    new BaseResponse<>(
                            "Success",
                            HttpStatus.OK.value(),
                            authResponse),
                    HttpStatus.OK
            );

        } catch (BadCredentialsException e) {
            return new ResponseEntity<>(
                    new BaseResponse<>(
                            "Error",
                            HttpStatus.UNAUTHORIZED.value(),
                            e.getMessage()
                    ),
                    HttpStatus.UNAUTHORIZED
            );
        }
    }

    @GetMapping("/users")
    public ResponseEntity<BaseResponse<?>> getUsers() {
        return new ResponseEntity<>(
                new BaseResponse<>(
                        "Success",
                        HttpStatus.OK.value(),
                        userService.getUsers()
                                .stream()
                                .map(user -> new ModelMapper().map(user, UserDto.class))
                                .collect(Collectors.toList())
                ),
                HttpStatus.OK
        );
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<BaseResponse<?>> getSingleUser(@PathVariable Long id) {
        return new ResponseEntity<>(
                new BaseResponse<>(
                        "Success",
                        HttpStatus.CREATED.value(),
                        new ModelMapper().map(userService.getUserById(id), UserDto.class)
                ),
                HttpStatus.CREATED
        );
    }

    @GetMapping("/church/{id}/users")
    public ResponseEntity<?> getUsersByChurch(@PathVariable Long id) {
        Church church = churchService.getChurch(id);
        return new ResponseEntity<>(
                new BaseResponse<>(
                        "Success",
                        HttpStatus.OK.value(),
                        userService.getUsersByChurch(church).stream().map(user -> new ModelMapper().map(user, UserDto.class))
                ),
                HttpStatus.OK
        );
    }

    @PostMapping("/roles")
    public ResponseEntity<BaseResponse<Role>> saveRole(@RequestBody Role role) {
        return new ResponseEntity<>(
                new BaseResponse<>(
                        "Success",
                        HttpStatus.CREATED.value(),
                        userService.saveRole(role)
                ),
                HttpStatus.CREATED
        );
    }

    @PostMapping("/role/addToUser")
    public ResponseEntity<BaseResponse<?>> addRoleToUser(@RequestBody AddRoleToUserRequest form) {
        userService.addRoleToUser(form.getUsername(), form.getRoleName());
        return new ResponseEntity<>(
                new BaseResponse<>(
                        "Success",
                        HttpStatus.OK.value(),
                        "Role added to user "
                ), HttpStatus.OK
        );
    }

    @GetMapping("/token/refresh")
    public ResponseEntity<BaseResponse<?>> refreshToken(HttpServletRequest request, HttpServletResponse response) {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            try {
                String refreshToken = authorizationHeader.substring("Bearer ".length());
                Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(refreshToken);
                String username = decodedJWT.getSubject();
                User user = userService.getUserByUserName(username);
                String accessToken = JWT.create()
                        .withSubject(user.getUsername())
                        .withExpiresAt(new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000))
                        .withIssuer(request.getRequestURL().toString())
                        .withClaim("roles", user.getRoles().stream().map(Role::getRoleName).collect(Collectors.toList()))
                        .sign(algorithm);

                Map<String, String> tokens = new HashMap<>();
                tokens.put("access_token", accessToken);
                tokens.put("refresh_token", refreshToken);
                return new ResponseEntity<>(
                        new BaseResponse<>(
                                "Success",
                                HttpStatus.OK.value(),
                                tokens
                        ),
                        HttpStatus.OK
                );

            } catch (Exception exception) {
                response.setHeader("Error", exception.getMessage());
                response.setStatus(FORBIDDEN.value());
                return new ResponseEntity<>(
                        new BaseResponse<>(
                                "Error",
                                FORBIDDEN.value(),
                                exception.getMessage()
                        ),
                        HttpStatus.OK
                );
            }
        } else {
            throw new RuntimeException("The refresh toke is missing");
        }
    }

    @PostMapping(
            path = "/user/{id}/profile",
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}
    )
    ResponseEntity<BaseResponse<?>> saveProfile(
            @PathVariable Long id,
            @RequestParam("profileImage") MultipartFile profileImage
    ) throws IOException {
        UploadProfilePictureDto pictureDto = new UploadProfilePictureDto(id, profileImage);
        return new ResponseEntity<>(
                new BaseResponse<>(
                        "Profile Image Saved!",
                        HttpStatus.OK.value(),
                        userService.uploadProfilePicture(pictureDto)
                ),
                HttpStatus.OK
        );
    }

    @GetMapping(path = "/confirm-token")
    public String confirm(@RequestParam("token") String token) {
        return userService.confirmToken(token);
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<BaseResponse<?>> forgotPassword(@RequestBody ForgotPasswordRequest request) {
        String response = userService.forgotPassword(request.getUsername());
        if (!response.startsWith("Invalid")) {
            return new ResponseEntity<>(
                    new BaseResponse<>(
                            "Success",
                            HttpStatus.OK.value(),
                            response
                    ), HttpStatus.OK
            );
        }
        return new ResponseEntity<>(
                new BaseResponse<>(
                        "Success",
                        HttpStatus.OK.value(),
                        "Invalid email address"
                ), HttpStatus.OK
        );
    }

    @PutMapping("/reset-password")
    public ResponseEntity<BaseResponse<?>> resetPassword(@RequestBody ResetPasswordRequest request) {
        return new ResponseEntity<>(
                new BaseResponse<>(
                        "Success",
                        HttpStatus.OK.value(),
                        userService.resetPassword(request.getToken(), request.getPassword())
                ), HttpStatus.OK
        );
    }
}


