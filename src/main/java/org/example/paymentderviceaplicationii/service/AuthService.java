package org.example.paymentderviceaplicationii.service;

import lombok.RequiredArgsConstructor;
import org.example.paymentderviceaplicationii.model.dto.LoginDTO;
import org.example.paymentderviceaplicationii.model.dto.UserDTO;
import org.example.paymentderviceaplicationii.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    private final JWTService jwtService;

    private final AuthenticationManager authenticationManager;

    private final UserService userServiceImpl;

    public String registerUser(UserDTO userDTO) {
        userServiceImpl.createUser(userDTO);

        return jwtService.generateToken(userDTO.getUsername());
    }

    public String loginUser(LoginDTO loginDTO) {
        String username = userRepository.findByEmail(loginDTO.getEmail()).getUsername();

        Authentication auth = authenticationManager
                .authenticate(
                        new UsernamePasswordAuthenticationToken(
                                username, loginDTO.getPassword())
                );

        if(auth.isAuthenticated()) {
            return jwtService.generateToken(
                    username
            );
        }

        return "Failed to authenticate";
    }
}
