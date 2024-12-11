package com.astrologix.user.service;

import com.astrologix.user.dto.UserResponseDTO;
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
        // Arrange
        User mockUser = new User(1L, "John", "Doe", "john.doe@example.com", "securePassword123",
                LocalDateTime.now(), LocalDateTime.now(), User.Status.ACTIVE);

        ZodiacResponse mockZodiac = new ZodiacResponse("ARIES", "Fire", "Cardinal", "Mars", "Dynamic and passionate");

        when(userRepository.findById(1L)).thenReturn(Optional.of(mockUser));
        when(astrologyIntegrationService.getZodiacDetails("03-25")).thenReturn(mockZodiac);

        // Act
        UserResponseDTO response = userService.getUserWithZodiac(1L);

        // Assert
        assertThat(response).isNotNull();
        assertThat(response.getId()).isEqualTo(1L);
        assertThat(response.getZodiacDetails()).isNotNull();
        assertThat(response.getZodiacDetails().getZodiacSign()).isEqualTo("ARIES");
    }

    @Test
    @DisplayName("Should throw exception when user not found")
    void shouldThrowExceptionWhenUserNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> {
            userService.getUserWithZodiac(1L);
        });
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
    @DisplayName("Should delegate zodiac details fetching to AstrologyIntegrationService")
    void shouldDelegateZodiacDetailsFetchingToAstrologyIntegrationService() {
        // Arrange
        String testDate = "03-25";
        ZodiacResponse mockZodiacResponse = new ZodiacResponse(
                "ARIES", "Fire", "Cardinal", "Mars", "Dynamic and passionate");

        when(astrologyIntegrationService.getZodiacDetails(testDate))
                .thenReturn(mockZodiacResponse);

        // Act
        ZodiacResponse result = astrologyIntegrationService.getZodiacDetails(testDate);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getZodiacSign()).isEqualTo("ARIES");
        verify(astrologyIntegrationService).getZodiacDetails(testDate);
    }
}
