package org.example.paymentderviceaplicationii.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.paymentderviceaplicationii.model.dto.LoginDTO;
import org.example.paymentderviceaplicationii.model.dto.UserDTO;
import org.example.paymentderviceaplicationii.service.AuthServiceImpl;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "API for user registration and login")
public class AuthController {
    private final AuthServiceImpl authServiceimpl;

    @Operation(summary = "Register a new user",
            description = "Creates a new user account and returns a JWT token")
    @ApiResponse(responseCode = "201",
            description = "Successfully registered",
            content = @Content(mediaType = "application/json"))
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserDTO userDTO) {
        String token = authServiceimpl.registerUser(userDTO);

        EntityModel<String> response = EntityModel.of(token);
        response.add(linkTo(methodOn(AuthController.class).register(userDTO)).withSelfRel());
        response.add(linkTo(methodOn(AuthController.class).login(new LoginDTO())).withRel("login"));
        response.add(Link.of("/swagger-ui.html#/Authentication/register").withRel("documentation"));

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

        EntityModel<String> response = EntityModel.of(token);
        response.add(linkTo(methodOn(AuthController.class).login(loginDTO)).withSelfRel());
        response.add(linkTo(methodOn(AuthController.class).register(new UserDTO())).withRel("register"));
        response.add(Link.of("/swagger-ui.html#/Authentication/login").withRel("documentation"));

        return ResponseEntity.ok(token);
    }
}
