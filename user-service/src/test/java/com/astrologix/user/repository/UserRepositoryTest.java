package com.astrologix.user.repository;

import com.astrologix.user.entity.User;
import com.astrologix.user.entity.User.Status;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        userRepository.deleteAll();

        // Create and save a user
        User user = new User(
                "John",
                "Doe",
                "john.doe@example.com",
                "password123",
                Status.ACTIVE
        );
        userRepository.save(user);
    }

    @Test
    public void shouldFindUserByEmail() {
        Optional<User> user = userRepository.findByEmail("john.doe@example.com");
        Assertions.assertTrue(user.isPresent());
        Assertions.assertEquals("John", user.get().getFirstName());
    }

    @Test
    public void shouldReturnEmptyForNonexistentEmail() {
        Optional<User> user = userRepository.findByEmail("nonexistent@example.com");
        Assertions.assertFalse(user.isPresent());
    }
}
