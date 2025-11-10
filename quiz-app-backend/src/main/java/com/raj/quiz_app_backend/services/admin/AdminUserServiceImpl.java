package com.raj.quiz_app_backend.services.admin;

import com.raj.quiz_app_backend.model.Role;
import com.raj.quiz_app_backend.model.User;
import com.raj.quiz_app_backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminUserServiceImpl implements AdminUserService {

    private final UserRepository userRepo;

    @Override
    public List<User> getAllUsers() { return userRepo.findAll(); }

    @Override
    public List<User> getUsersByRole(String role) {
        return userRepo.findAll().stream()
                .filter(u -> u.getRole().name().equalsIgnoreCase(role))
                .collect(Collectors.toList());
    }

    @Override
    public List<User> searchUsers(String keyword) {
        return userRepo.findAll().stream()
                .filter(u -> u.getEmail().contains(keyword) || u.getUsername().contains(keyword))
                .collect(Collectors.toList());
    }

    @Override
    public User changeUserRole(String userId, String role) {
        User user = userRepo.findById(userId).orElseThrow();
        user.setRole(Role.valueOf(role.toUpperCase()));
        return userRepo.save(user);
    }

    @Override
    public void toggleUserStatus(String userId, boolean enabled) {
        User user = userRepo.findById(userId).orElseThrow();
        user.setEnabled(enabled);
        userRepo.save(user);
    }

    @Override
    public void deleteUser(String userId) {
        userRepo.deleteById(userId);
    }

    @Override
    public long countTotalUsers() {
        return userRepo.count();
    }
}