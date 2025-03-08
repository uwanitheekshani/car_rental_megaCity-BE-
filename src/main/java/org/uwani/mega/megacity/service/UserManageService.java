package org.uwani.mega.megacity.service;


import org.uwani.mega.megacity.entity.User;

import java.util.List;

public interface UserManageService {
    List<User> getAllUsers();

    List<User> getUsersByEmail(String email);
    List<User> getUsersByRole(String role);
    boolean updateUser(int userId, User updatedUser);
    boolean deleteUser(int userId);
}
