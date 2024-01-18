package com.demo.book.service.impl;

import com.demo.book.entity.Category;
import com.demo.book.entity.Librarian;
import com.demo.book.entity.Role;
import com.demo.book.entity.User;
import com.demo.book.entity.enums.UserType;
import com.demo.book.factory.ServiceAbstractFactory;
import com.demo.book.factory.UserFactory.UserAbstractFactory;
import com.demo.book.factory.UserFactory.UserFactory;
import com.demo.book.repository.LibrarianRepository;
import com.demo.book.repository.RoleRepository;
import com.demo.book.repository.UserRepository;
import com.demo.book.service.AdminService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private LibrarianRepository librarianRepository;
    @Autowired
    private RoleRepository roleRepository;
    public AdminServiceImpl() {

    }
    @Transactional
    @Override
    public Librarian createLibrarianUser(Librarian user) {
        UserAbstractFactory factory = UserFactory.getFactory(UserType.LIBRARIAN);

        Librarian librarian = (Librarian) factory.createUser();
        Role role1 = roleRepository.findByRole("MEMBER");
        Role role2 = roleRepository.findByRole("LIBRARIAN");

        librarian.setDisplayName(user.getDisplayName());
        librarian.setAge(user.getAge());
        librarian.setUsername(user.getUsername());
        librarian.setPassword(user.getPassword());
        librarian.setRoles(Arrays.asList(role1,role2));

        return librarianRepository.save(librarian);
    }

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }
}
