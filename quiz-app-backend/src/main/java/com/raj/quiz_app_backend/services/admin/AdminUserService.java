package com.raj.quiz_app_backend.services.admin;


import com.raj.quiz_app_backend.model.User;
import java.util.List;

public interface AdminUserService {
    List<User> getAllUsers(); // get all users
    List<User> getUsersByRole(String role); // filter by role
    List<User> searchUsers(String keyword); // search users
    User changeUserRole(String userId, String role); // change role
    void toggleUserStatus(String userId, boolean enabled); // enable/disable
    void deleteUser(String userId); // delete user
    long countTotalUsers(); // count total users
}
