package com.astrologix.user.service;

import com.astrologix.user.dto.UserResponse;
import com.astrologix.user.dto.ZodiacResponse;
import com.astrologix.user.entity.User;
import com.astrologix.user.exception.UserNotFoundException;
import com.astrologix.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final AstrologyIntegrationService astrologyIntegrationService;

    @Autowired
    public UserService(UserRepository userRepository, AstrologyIntegrationService astrologyIntegrationService) {
        this.userRepository = userRepository;
        this.astrologyIntegrationService = astrologyIntegrationService;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public User createUser(User user) {
        validateEmailUniqueness(user.getEmail());
        setTimestampsForCreation(user);
        return userRepository.save(user);
    }

    public User updateUser(Long id, User user) {
        User existingUser = findUserById(id);
        updateUserFields(existingUser, user);
        existingUser.setUpdatedAt(LocalDateTime.now());
        return userRepository.save(existingUser);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public UserResponse getUserWithZodiac(Long userId) {
        User user = findUserById(userId);
        String dateOfBirth = extractDateOfBirth(user); // Stubbed logic
        ZodiacResponse zodiacDetails = astrologyIntegrationService.getZodiacDetails(dateOfBirth);
        return mapToUserResponseDTO(user, zodiacDetails);
    }

    private void validateEmailUniqueness(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email already exists");
        }
    }

    private void setTimestampsForCreation(User user) {
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        user.setStatus(User.Status.ACTIVE);
    }

    private User findUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    private void updateUserFields(User existingUser, User updatedUser) {
        existingUser.setFirstName(updatedUser.getFirstName());
        existingUser.setLastName(updatedUser.getLastName());
        existingUser.setEmail(updatedUser.getEmail());
        if (updatedUser.getPassword() != null && !updatedUser.getPassword().isBlank()) {
            existingUser.setPassword(updatedUser.getPassword());
        }
    }

    private String extractDateOfBirth(User user) {
        // Placeholder logic. Replace with actual date of birth field when added to the User entity.
        return "03-25";
    }

    private UserResponse mapToUserResponseDTO(User user, ZodiacResponse zodiacDetails) {
        return new UserResponse(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                zodiacDetails
        );
    }
}
