package org.example.paymentderviceaplicationii.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.paymentderviceaplicationii.model.User;
import org.example.paymentderviceaplicationii.model.dto.UserDTO;
import org.example.paymentderviceaplicationii.service.UserServiceImpl;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Tag(name = "Users", description = "Operations related to user management")
public class UserController {

    private final UserServiceImpl userServiceimpl;

    @Operation(summary = "Create a new user (ADMIN only)")
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<EntityModel<User>> createUser(@RequestBody UserDTO userDTO) {
        User user = userServiceimpl.createUser(userDTO);
        EntityModel<User> resource = toUserModel(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(resource);
    }

    @Operation(summary = "Get all users (ADMIN only)")
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CollectionModel<EntityModel<User>>> getAllUsers() {
        List<User> users = userServiceimpl.getAllUsers();
        List<EntityModel<User>> userModels = users.stream()
                .map(this::toUserModel)
                .collect(Collectors.toList());

        CollectionModel<EntityModel<User>> collection = CollectionModel.of(userModels);
        collection.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UserController.class)
                .getAllUsers()).withSelfRel());

        return ResponseEntity.ok(collection);
    }

    @Operation(summary = "Get current authenticated user")
    @GetMapping("/me")
    public ResponseEntity<EntityModel<User>> getCurrentUser(@AuthenticationPrincipal User user) {
        EntityModel<User> resource = toUserModel(user);
        return ResponseEntity.ok(resource);
    }

    @Operation(summary = "Update user by ID")
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<User>> updateUser(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        User updated = userServiceimpl.updateUser(id, userDTO);
        EntityModel<User> resource = toUserModel(updated);
        return ResponseEntity.ok(resource);
    }

    @Operation(summary = "Delete user by ID (ADMIN only)")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userServiceimpl.deleteUser(id);
        return ResponseEntity.noContent().build();
    }


    private EntityModel<User> toUserModel(User user) {
        EntityModel<User> model = EntityModel.of(user);
        model.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UserController.class)
                .getCurrentUser(user)).withRel("self"));
        model.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UserController.class)
                .updateUser(user.getId(), null)).withRel("update"));
        model.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UserController.class)
                .deleteUser(user.getId())).withRel("delete"));
        return model;
    }
}