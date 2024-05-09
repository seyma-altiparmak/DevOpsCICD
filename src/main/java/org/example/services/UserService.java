package org.example.services;

import org.example.model.User;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();
    void saveUsers(User user);
    User getUserById(long id);

    void deleteUserById(long id);
}
