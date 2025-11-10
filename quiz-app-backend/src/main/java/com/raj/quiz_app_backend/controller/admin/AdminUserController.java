package com.raj.quiz_app_backend.controller.admin;

import com.raj.quiz_app_backend.model.User;
import com.raj.quiz_app_backend.services.admin.AdminUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/users")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminUserController {

    private final AdminUserService adminUserService;

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(adminUserService.getAllUsers());
    }

    @GetMapping("/role/{role}")
    public ResponseEntity<List<User>> getByRole(@PathVariable String role) {
        return ResponseEntity.ok(adminUserService.getUsersByRole(role));
    }

    @GetMapping("/search")
    public ResponseEntity<List<User>> search(@RequestParam String keyword) {
        return ResponseEntity.ok(adminUserService.searchUsers(keyword));
    }

    @PutMapping("/{id}/role")
    public ResponseEntity<User> changeRole(@PathVariable String id, @RequestParam String role) {
        return ResponseEntity.ok(adminUserService.changeUserRole(id, role));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Void> toggleStatus(@PathVariable String id, @RequestParam boolean enabled) {
        adminUserService.toggleUserStatus(id, enabled);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable String id) {
        adminUserService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/count")
    public ResponseEntity<Long> count() {
        return ResponseEntity.ok(adminUserService.countTotalUsers());
    }
}