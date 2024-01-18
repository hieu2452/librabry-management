package com.demo.book.factory;

import com.demo.book.entity.Role;
import com.demo.book.entity.User;
import com.demo.book.repository.RoleRepository;
import com.demo.book.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
public class IUserImpl implements IUser{
    private final Logger log = LoggerFactory.getLogger(IUserImpl.class);
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> findUserById(long id) {
        return userRepository.findById(id);
    }

    @Override
    public User createUser(User user) {
//        User existingUser = userRepository.findByUsername(user.get());
//        if(existingUser != null) {
//            throw new RuntimeException("User already exist");
//        }
//        user.setRoles(user.getRoles());
//        return userRepository.save(user);]
        return null;
    }

    @Override
    public User updateUser(User user) {
        return null;
    }

    @Override
    public User updateUsername(String name) {
        return null;
    }

    @Override
    public void deleteUser(long id) {
        try {
            User user = userRepository.findById(id).orElse(null);
            assert user != null;
            userRepository.delete(user);
        } catch (Exception e) {
            log.debug(e.getMessage());
            throw new RuntimeException("Unable to delete user");
        }
    }
}
