package com.astrologix.user.controller;

import com.astrologix.user.entity.User;
import com.astrologix.user.exception.UserNotFoundException;
import com.astrologix.user.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    @DisplayName("Get all users should return 200 OK with empty list")
    public void whenGetUsersShouldReturnOKWithEmptyList() throws Exception {
        when(userService.getAllUsers()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    @DisplayName("Get all users should return 200 OK with non-empty list")
    public void whenGetUsersShouldReturnOKWithNonEmptyList() throws Exception {
        List<User> users = List.of(new User(1L, "John", "Doe", "john.doe@example.com"));
        when(userService.getAllUsers()).thenReturn(users);

        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].firstName").value("John"));
    }

    @Test
    @DisplayName("Get user by ID should return user details")
    public void whenGetUserByIdShouldReturnUser() throws Exception {
        User user = new User(1L, "John", "Doe", "john.doe@example.com");
        when(userService.getUserById(1L)).thenReturn(Optional.of(user));

        mockMvc.perform(get("/api/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"))
                .andExpect(jsonPath("$.email").value("john.doe@example.com"));
    }

    @Test
    @DisplayName("Get user by non-existent ID should return 404 Not Found")
    public void whenGetUserByIdNotFoundShouldReturn404() throws Exception {
        when(userService.getUserById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/users/1"))
                .andExpect(status().isNotFound())
                .andExpect(content().string(""));
    }

    @Test
    @DisplayName("Create user should return created user details")
    public void whenCreateUserShouldReturnUser() throws Exception {
        User createdUser = new User(1L, "John", "Doe", "john.doe@example.com", "securePassword123",
                LocalDateTime.now(), LocalDateTime.now(), User.Status.ACTIVE);

        when(userService.createUser(any(User.class))).thenReturn(createdUser);

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "firstName": "John",
                                    "lastName": "Doe",
                                    "email": "john.doe@example.com",
                                    "password": "securePassword123"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"))
                .andExpect(jsonPath("$.email").value("john.doe@example.com"));
    }

    @Test
    @DisplayName("Create user with invalid input should return 400 Bad Request")
    public void whenCreateUserWithInvalidInputShouldReturnBadRequest() throws Exception {
        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "firstName": "",
                                    "lastName": "Doe",
                                    "email": ""
                                }
                                """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.firstName").value("First name is mandatory"))
                .andExpect(jsonPath("$.email").value("Email is mandatory"));
    }

    @Test
    @DisplayName("Create user with duplicate email should return 409 Conflict")
    public void whenCreateUserWithDuplicateEmailShouldReturnConflict() throws Exception {
        when(userService.createUser(any(User.class)))
                .thenThrow(new IllegalArgumentException("Email already exists"));

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "firstName": "John",
                                    "lastName": "Doe",
                                    "email": "john.doe@example.com",
                                    "password": "securePassword123"
                                }
                                """))
                .andExpect(status().isConflict())
                .andExpect(content().string("Email already exists"));
    }

    @Test
    @DisplayName("Update existing user should return updated user details")
    public void whenUpdateUserShouldReturnUpdatedUser() throws Exception {
        User updatedUser = new User(1L, "Jane", "Doe", "jane.doe@example.com");

        when(userService.updateUser(eq(1L), any(User.class))).thenReturn(updatedUser);

        mockMvc.perform(put("/api/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "firstName": "Jane",
                                    "lastName": "Doe",
                                    "email": "jane.doe@example.com",
                                    "password": "securePassword123"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.firstName").value("Jane"))
                .andExpect(jsonPath("$.lastName").value("Doe"))
                .andExpect(jsonPath("$.email").value("jane.doe@example.com"));
    }

    @Test
    @DisplayName("Update user with invalid email should return 400 Bad Request")
    public void whenUpdateUserWithInvalidEmailShouldReturnBadRequest() throws Exception {
        mockMvc.perform(put("/api/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "firstName": "Jane",
                                    "lastName": "Doe",
                                    "email": "invalidemail"
                                }
                                """))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Delete existing user should return 204 No Content")
    public void whenDeleteUserShouldReturnNoContent() throws Exception {
        doNothing().when(userService).deleteUser(1L);

        mockMvc.perform(delete("/api/users/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Delete non-existent user should return 404 Not Found")
    public void whenDeleteNonExistingUserShouldReturnNotFound() throws Exception {
        doThrow(new UserNotFoundException("User not found")).when(userService).deleteUser(999L);

        mockMvc.perform(delete("/api/users/999"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("User not found"));
    }

    @Test
    @DisplayName("Update non-existent user should return 404 Not Found")
    public void whenUpdateNonExistentUserThenReturn404() throws Exception {
        Long nonExistentUserId = 999L;
        String requestBody = """
        {
            "firstName": "John",
            "lastName": "Doe",
            "email": "john.doe@example.com",
            "password": "securePassword123"
        }
    """;

        when(userService.updateUser(eq(nonExistentUserId), any(User.class)))
                .thenThrow(new UserNotFoundException("User not found"));

        mockMvc.perform(put("/api/users/" + nonExistentUserId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isNotFound())
                .andExpect(content().string("User not found"));
    }
}
