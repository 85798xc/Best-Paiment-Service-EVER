package org.example.paymentderviceaplicationii.service;

import org.example.paymentderviceaplicationii.model.User;

import java.util.List;

public interface UserService {
    User getUserById(Long userId);

    User getUserByEmail(String email);

    User getByUsername(String username);

    List<User> getAllUsers();

    void deleteUserById(Long userId);

    void deleteUserByEmail(String email);

    void deleteAllUsers();
}
