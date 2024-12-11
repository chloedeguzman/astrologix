package com.astrologix.user.service;

import com.astrologix.user.dto.UserResponse;
import com.astrologix.user.dto.ZodiacResponse;
import com.astrologix.user.entity.User;
import com.astrologix.user.exception.UserNotFoundException;
import com.astrologix.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private AstrologyIntegrationService astrologyIntegrationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should fetch user with zodiac details")
    void shouldFetchUserWithZodiacDetails() {
        User mockUser = new User(1L, "John", "Doe", "john.doe@example.com", "securePassword123",
                LocalDateTime.now(), LocalDateTime.now(), User.Status.ACTIVE);

        ZodiacResponse mockZodiac = new ZodiacResponse("ARIES", "Fire", "Cardinal", "Mars", "Dynamic and passionate");

        when(userRepository.findById(1L)).thenReturn(Optional.of(mockUser));
        when(astrologyIntegrationService.getZodiacDetails("03-25")).thenReturn(mockZodiac);

        UserResponse response = userService.getUserWithZodiac(1L);

        assertThat(response).isNotNull();
        assertThat(response.getId()).isEqualTo(1L);
        assertThat(response.getZodiacDetails()).isNotNull();
        assertThat(response.getZodiacDetails().getZodiacSign()).isEqualTo("ARIES");
    }

    @Test
    @DisplayName("Should throw exception when user not found")
    void shouldThrowExceptionWhenUserNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.getUserWithZodiac(1L));
    }

    @Test
    @DisplayName("Create user with duplicate email should throw exception")
    void whenCreateUserWithDuplicateEmailThenThrowException() {
        User user = new User(null, "Jane", "Doe", "jane.doe@example.com", "securePassword123", null, null, User.Status.ACTIVE);
        when(userRepository.existsByEmail("jane.doe@example.com")).thenReturn(true);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.createUser(user);
        });

        assertEquals("Email already exists", exception.getMessage());
    }

    @Test
    @DisplayName("Create user successfully")
    void shouldCreateUserSuccessfully() {
        User newUser = new User(null, "Alice", "Smith", "alice.smith@example.com", "securePassword123",
                null, null, User.Status.ACTIVE);
        User savedUser = new User(1L, "Alice", "Smith", "alice.smith@example.com", "securePassword123",
                LocalDateTime.now(), LocalDateTime.now(), User.Status.ACTIVE);

        when(userRepository.existsByEmail("alice.smith@example.com")).thenReturn(false);
        when(userRepository.save(newUser)).thenReturn(savedUser);

        User result = userService.createUser(newUser);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Alice", result.getFirstName());
        verify(userRepository).save(newUser);
    }

    @Test
    @DisplayName("Update user successfully")
    void shouldUpdateUserSuccessfully() {
        Long userId = 1L;
        User existingUser = new User(userId, "Bob", "Johnson", "bob.johnson@example.com", "securePassword123",
                LocalDateTime.now(), LocalDateTime.now(), User.Status.ACTIVE);

        User updatedData = new User(userId, "Bobby", "Johnson", "bobby.johnson@example.com", null,
                null, null, null);

        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(any(User.class))).thenReturn(updatedData);

        User result = userService.updateUser(userId, updatedData);

        assertNotNull(result);
        assertEquals("Bobby", result.getFirstName());
        assertEquals("bobby.johnson@example.com", result.getEmail());
        verify(userRepository).save(any(User.class));
    }

    @Test
    @DisplayName("Update user - user not found")
    void shouldThrowExceptionWhenUpdatingNonExistentUser() {
        Long userId = 1L;
        User updatedData = new User(userId, "Bobby", "Johnson", "bobby.johnson@example.com", null,
                null, null, null);

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.updateUser(userId, updatedData));
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    @DisplayName("Should delete user successfully")
    void shouldDeleteUserSuccessfully() {
        Long userId = 1L;

        doNothing().when(userRepository).deleteById(userId);

        userService.deleteUser(userId);

        verify(userRepository).deleteById(userId);
    }

    @Test
    @DisplayName("Should delegate zodiac details fetching to AstrologyIntegrationService")
    void shouldDelegateZodiacDetailsFetchingToAstrologyIntegrationService() {
        String testDate = "03-25";
        ZodiacResponse mockZodiacResponse = new ZodiacResponse("ARIES", "Fire", "Cardinal", "Mars", "Dynamic and passionate");

        when(astrologyIntegrationService.getZodiacDetails(testDate)).thenReturn(mockZodiacResponse);

        ZodiacResponse result = astrologyIntegrationService.getZodiacDetails(testDate);

        assertThat(result).isNotNull();
        assertThat(result.getZodiacSign()).isEqualTo("ARIES");
        verify(astrologyIntegrationService).getZodiacDetails(testDate);
    }
}
