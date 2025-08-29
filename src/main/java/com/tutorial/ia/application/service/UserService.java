package com.tutorial.ia.application.service;

import com.tutorial.ia.domain.exception.DuplicateResourceException;
import com.tutorial.ia.domain.exception.UserNotFoundException;
import com.tutorial.ia.domain.model.User;
import com.tutorial.ia.domain.port.in.UserManagementUseCase;
import com.tutorial.ia.domain.port.out.UserRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserManagementUseCase {

    private final UserRepositoryPort userRepositoryPort;

    @Override
    public User createUser(String username, String email, String password) {
        if (!isUsernameAvailable(username)) {
            throw new DuplicateResourceException("User", "username", username);
        }
        if (!isEmailAvailable(email)) {
            throw new DuplicateResourceException("User", "email", email);
        }
        
        User user = new User(username, email, password);
        return userRepositoryPort.save(user);
    }

    @Override
    public Optional<User> getUserById(Long id) {
        return userRepositoryPort.findById(id);
    }

    @Override
    public Optional<User> getUserByUsername(String username) {
        return userRepositoryPort.findByUsername(username);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepositoryPort.findAll();
    }

    @Override
    public User updateUser(Long id, String username, String email) {
        User user = userRepositoryPort.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        
        if (!user.getUsername().equals(username) && !isUsernameAvailable(username)) {
            throw new DuplicateResourceException("User", "username", username);
        }
        if (!user.getEmail().equals(email) && !isEmailAvailable(email)) {
            throw new DuplicateResourceException("User", "email", email);
        }
        
        user.setUsername(username);
        user.setEmail(email);
        return userRepositoryPort.save(user);
    }

    @Override
    public void deleteUser(Long id) {
        userRepositoryPort.deleteById(id);
    }

    @Override
    public boolean isUsernameAvailable(String username) {
        return !userRepositoryPort.existsByUsername(username);
    }

    @Override
    public boolean isEmailAvailable(String email) {
        return !userRepositoryPort.existsByEmail(email);
    }
}