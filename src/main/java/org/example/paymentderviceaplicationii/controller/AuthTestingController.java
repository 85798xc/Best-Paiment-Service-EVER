package org.example.paymentderviceaplicationii.controller;

import lombok.RequiredArgsConstructor;
import org.example.paymentderviceaplicationii.model.User;
import org.example.paymentderviceaplicationii.model.enums.Role;
import org.example.paymentderviceaplicationii.repository.UserRepository;
import org.example.paymentderviceaplicationii.service.JWTService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth-test")
@RequiredArgsConstructor
public class AuthTestingController {

    private final UserRepository  userRepository;

    private final JWTService  jwtService;

    private final BCryptPasswordEncoder encoder;

    @PostMapping
    public String authTest() {
        User user = new User();

        user.setEmail("test@gmail.com");
        user.setUsername("testUser");
        user.setPassword(encoder.encode("password"));
        user.setRole(Role.USER);

        userRepository.save(user);

        return jwtService.generateToken(user.getUsername());
    }
}
