package org.example.paymentderviceaplicationii.controller;

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
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserDTO userDTO) {
        String token = authService.registerUser(userDTO);
        return ResponseEntity.ok(token);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDTO loginDTO) {
        String token = authService.loginUser(loginDTO);
        return ResponseEntity.ok(token);
    }
}
