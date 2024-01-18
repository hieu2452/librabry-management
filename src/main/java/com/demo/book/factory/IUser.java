package com.demo.book.factory;

import com.demo.book.entity.User;

import java.util.List;
import java.util.Optional;

public interface IUser {
    List<User> findAllUsers();
    Optional<User> findUserById(long id);
    User createUser(User user);
    User updateUser(User user);
    User updateUsername(String name);
    void deleteUser(long id);
}
