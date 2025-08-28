package com.tutorial.ia.domain.port.in;

import com.tutorial.ia.domain.model.User;
import java.util.List;
import java.util.Optional;

public interface UserManagementUseCase {
    User createUser(String username, String email, String password);
    Optional<User> getUserById(Long id);
    Optional<User> getUserByUsername(String username);
    List<User> getAllUsers();
    User updateUser(Long id, String username, String email);
    void deleteUser(Long id);
    boolean isUsernameAvailable(String username);
    boolean isEmailAvailable(String email);
}