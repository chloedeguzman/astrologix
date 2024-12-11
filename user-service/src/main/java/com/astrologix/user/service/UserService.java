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
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        user.setStatus(User.Status.ACTIVE);
        return userRepository.save(user);
    }

    public User updateUser(Long id, User user) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        existingUser.setFirstName(user.getFirstName());
        existingUser.setLastName(user.getLastName());
        existingUser.setEmail(user.getEmail());
        if (user.getPassword() != null && !user.getPassword().isBlank()) {
            existingUser.setPassword(user.getPassword());
        }
        existingUser.setUpdatedAt(LocalDateTime.now());
        return userRepository.save(existingUser);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public UserResponse getUserWithZodiac(Long userId) {
        // Fetch user from the database
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        // Extract date of birth (stubbed as "03-25" for now)
        // Replace this with actual logic when date of birth is added to User entity
        String dateOfBirth = "03-25";

        // Fetch zodiac details
        ZodiacResponse zodiacDetails = astrologyIntegrationService.getZodiacDetails(dateOfBirth);

        // Map user and zodiac details to the response DTO
        return new UserResponse(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                zodiacDetails
        );
    }
}
