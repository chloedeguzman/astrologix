package com.astrologix.user.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UserTest {

    private User user;

    @BeforeEach
    void setUp() {
        user = new User(1L, "John", "Doe", "john.doe@example.com", "securePassword123",
                LocalDateTime.now(), LocalDateTime.now(), User.Status.ACTIVE);
    }

    @Test
    @DisplayName("Should correctly set and get fields")
    void shouldSetAndGetFields() {
        assertThat(user.getFirstName()).isEqualTo("John");
        assertThat(user.getLastName()).isEqualTo("Doe");
        assertThat(user.getEmail()).isEqualTo("john.doe@example.com");
    }

    @Test
    @DisplayName("Should throw exception for invalid email in updateEmail")
    void shouldThrowExceptionForInvalidEmail() {
        assertThrows(IllegalArgumentException.class, () -> user.updateEmail("invalid-email"));
    }

    @Test
    @DisplayName("Should update email correctly")
    void shouldUpdateEmailCorrectly() {
        user.updateEmail("new.email@example.com");
        assertThat(user.getEmail()).isEqualTo("new.email@example.com");
    }

    @Test
    @DisplayName("Should update password correctly")
    void shouldUpdatePasswordCorrectly() {
        user.updatePassword("newSecurePassword123");
        assertThat(user.getPassword()).isEqualTo("newSecurePassword123");
    }

    @Test
    @DisplayName("Should correctly implement equals and hashCode")
    void shouldImplementEqualsAndHashCode() {
        LocalDateTime fixedTime = LocalDateTime.of(2024, 12, 11, 15, 56, 12);
        User user1 = new User(1L, "John", "Doe", "john.doe@example.com", "securePassword123",
                fixedTime, fixedTime, User.Status.ACTIVE);
        User user2 = new User(1L, "John", "Doe", "john.doe@example.com", "securePassword123",
                fixedTime, fixedTime, User.Status.ACTIVE);
        User user3 = new User(2L, "Jane", "Doe", "jane.doe@example.com", "securePassword123",
                fixedTime, fixedTime, User.Status.ACTIVE);

        assertThat(user1).isEqualTo(user2);
        assertThat(user1).hasSameHashCodeAs(user2);
        assertThat(user1).isNotEqualTo(user3);
    }


    @Test
    @DisplayName("Should correctly set timestamps on creation")
    void shouldSetTimestampsOnCreation() {
        LocalDateTime now = LocalDateTime.now();
        user.setCreatedAt(now);
        user.setUpdatedAt(now);

        assertThat(user.getCreatedAt()).isEqualTo(now);
        assertThat(user.getUpdatedAt()).isEqualTo(now);
    }
}
