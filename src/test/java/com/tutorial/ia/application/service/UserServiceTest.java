package com.tutorial.ia.application.service;

import com.tutorial.ia.domain.exception.DuplicateResourceException;
import com.tutorial.ia.domain.model.User;
import com.tutorial.ia.domain.port.out.UserRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepositoryPort userRepositoryPort;

    private UserService userService;

    @BeforeEach
    void setUp() {
        userService = new UserService(userRepositoryPort);
    }

    @Test
    void createUser_ShouldReturnUser_WhenValidData() {
        String username = "testuser";
        String email = "test@example.com";
        String password = "password123";

        when(userRepositoryPort.existsByUsername(username)).thenReturn(false);
        when(userRepositoryPort.existsByEmail(email)).thenReturn(false);
        when(userRepositoryPort.save(any(User.class))).thenAnswer(invocation -> {
            User user = invocation.getArgument(0);
            user.setId(1L);
            return user;
        });

        User result = userService.createUser(username, email, password);

        assertNotNull(result);
        assertEquals(username, result.getUsername());
        assertEquals(email, result.getEmail());
        assertEquals(password, result.getPassword());
        assertEquals(1L, result.getId());

        verify(userRepositoryPort).existsByUsername(username);
        verify(userRepositoryPort).existsByEmail(email);
        verify(userRepositoryPort).save(any(User.class));
    }

    @Test
    void createUser_ShouldThrowException_WhenUsernameExists() {
        String username = "existinguser";
        String email = "test@example.com";
        String password = "password123";

        when(userRepositoryPort.existsByUsername(username)).thenReturn(true);

        DuplicateResourceException exception = assertThrows(
            DuplicateResourceException.class,
            () -> userService.createUser(username, email, password)
        );

        assertEquals("User with username 'existinguser' already exists", exception.getMessage());
        verify(userRepositoryPort).existsByUsername(username);
        verify(userRepositoryPort, never()).save(any(User.class));
    }

    @Test
    void createUser_ShouldThrowException_WhenEmailExists() {
        String username = "testuser";
        String email = "existing@example.com";
        String password = "password123";

        when(userRepositoryPort.existsByUsername(username)).thenReturn(false);
        when(userRepositoryPort.existsByEmail(email)).thenReturn(true);

        DuplicateResourceException exception = assertThrows(
            DuplicateResourceException.class,
            () -> userService.createUser(username, email, password)
        );

        assertEquals("User with email 'existing@example.com' already exists", exception.getMessage());
        verify(userRepositoryPort).existsByUsername(username);
        verify(userRepositoryPort).existsByEmail(email);
        verify(userRepositoryPort, never()).save(any(User.class));
    }

    @Test
    void getUserById_ShouldReturnUser_WhenUserExists() {
        Long userId = 1L;
        User user = new User("testuser", "test@example.com", "password123");
        user.setId(userId);

        when(userRepositoryPort.findById(userId)).thenReturn(Optional.of(user));

        Optional<User> result = userService.getUserById(userId);

        assertTrue(result.isPresent());
        assertEquals(userId, result.get().getId());
        assertEquals("testuser", result.get().getUsername());
    }

    @Test
    void getUserById_ShouldReturnEmpty_WhenUserNotFound() {
        Long userId = 999L;

        when(userRepositoryPort.findById(userId)).thenReturn(Optional.empty());

        Optional<User> result = userService.getUserById(userId);

        assertFalse(result.isPresent());
    }
}