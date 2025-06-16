package org.example.paymentderviceaplicationii.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.paymentderviceaplicationii.model.dto.LoginDTO;
import org.example.paymentderviceaplicationii.model.dto.UserDTO;
import org.example.paymentderviceaplicationii.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "API for user registration and login")
public class AuthController {
    private final AuthService authServiceimpl;

    @Operation(summary = "Register a new user",
            description = "Creates a new user account and returns a JWT token")
    @ApiResponse(responseCode = "201",
            description = "Successfully registered",
            content = @Content(mediaType = "application/json"))
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserDTO userDTO) {
        String token = authServiceimpl.registerUser(userDTO);

        return ResponseEntity.ok(token);
    }

    @Operation(summary = "Login with credentials",
            description = "Authenticates a user and returns a JWT token")
    @ApiResponse(responseCode = "200",
            description = "Successfully logged in",
            content = @Content(mediaType = "application/json"))
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDTO loginDTO) {
        String token = authServiceimpl.loginUser(loginDTO);

        return ResponseEntity.ok(token);
    }
}
