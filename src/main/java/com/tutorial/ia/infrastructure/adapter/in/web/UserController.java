package com.tutorial.ia.infrastructure.adapter.in.web;

import com.tutorial.ia.domain.model.User;
import com.tutorial.ia.domain.port.in.UserManagementUseCase;
import com.tutorial.ia.infrastructure.adapter.in.web.dto.CreateUserRequest;
import com.tutorial.ia.infrastructure.adapter.in.web.dto.UpdateUserRequest;
import com.tutorial.ia.infrastructure.adapter.in.web.dto.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserManagementUseCase userManagementUseCase;

    @PostMapping
    public ResponseEntity<UserResponse> createUser(@RequestBody CreateUserRequest request) {
        try {
            User user = userManagementUseCase.createUser(
                    request.getUsername(), 
                    request.getEmail(), 
                    request.getPassword()
            );
            return ResponseEntity.status(HttpStatus.CREATED).body(UserResponse.from(user));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) {
        return userManagementUseCase.getUserById(id)
                .map(user -> ResponseEntity.ok(UserResponse.from(user)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        List<UserResponse> users = userManagementUseCase.getAllUsers().stream()
                .map(UserResponse::from)
                .collect(Collectors.toList());
        return ResponseEntity.ok(users);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable Long id, @RequestBody UpdateUserRequest request) {
        try {
            User user = userManagementUseCase.updateUser(id, request.getUsername(), request.getEmail());
            return ResponseEntity.ok(UserResponse.from(user));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userManagementUseCase.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/check-username/{username}")
    public ResponseEntity<Boolean> checkUsernameAvailability(@PathVariable String username) {
        boolean available = userManagementUseCase.isUsernameAvailable(username);
        return ResponseEntity.ok(available);
    }

    @GetMapping("/check-email/{email}")
    public ResponseEntity<Boolean> checkEmailAvailability(@PathVariable String email) {
        boolean available = userManagementUseCase.isEmailAvailable(email);
        return ResponseEntity.ok(available);
    }
}