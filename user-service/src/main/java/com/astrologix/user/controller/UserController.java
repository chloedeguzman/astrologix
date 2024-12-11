package com.astrologix.user.controller;

import com.astrologix.user.entity.User;
import com.astrologix.user.exception.UserNotFoundException;
import com.astrologix.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@Tag(name = "User API", description = "Operations related to user management")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Get All Users", description = "Retrieve a list of all users in the system")
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @Operation(summary = "Get User by ID", description = "Retrieve a user by their unique ID")
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(
            @Parameter(description = "The ID of the user to retrieve") @PathVariable Long id) {
        return userService.getUserById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Create a New User", description = "Add a new user to the system")
    @PostMapping
    public ResponseEntity<User> createUser(
            @Parameter(description = "The user details to be added") @Valid @RequestBody User user) {
        User createdUser = userService.createUser(user);
        return ResponseEntity.ok(createdUser);
    }

    @Operation(summary = "Update an Existing User", description = "Modify details of an existing user")
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(
            @Parameter(description = "The ID of the user to update") @PathVariable Long id,
            @Parameter(description = "The updated user details") @Valid @RequestBody User user) {
        User updatedUser = userService.updateUser(id, user);
        return ResponseEntity.ok(updatedUser);
    }

    @Operation(summary = "Delete a User", description = "Remove a user from the system")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(
            @Parameter(description = "The ID of the user to delete") @PathVariable Long id) {
        try {
            userService.deleteUser(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            throw new UserNotFoundException("User not found");
        }
    }
}
