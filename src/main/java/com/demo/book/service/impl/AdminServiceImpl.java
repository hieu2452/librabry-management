package com.demo.book.service.impl;

import com.demo.book.dto.UserDto;
import com.demo.book.entity.*;
import com.demo.book.entity.enums.UserType;
import com.demo.book.factory.ServiceAbstractFactory;
import com.demo.book.factory.UserFactory.UserAbstractFactory;
import com.demo.book.factory.UserFactory.UserFactory;
import com.demo.book.repository.LibrarianRepository;
import com.demo.book.repository.MemberRepository;
import com.demo.book.repository.RoleRepository;
import com.demo.book.repository.UserRepository;
import com.demo.book.service.AdminService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private LibrarianRepository librarianRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private MemberRepository memberRepository;

    public AdminServiceImpl() {

    }

    @Transactional
    @Override
    public Librarian createLibrarianUser(Librarian user) {
        UserAbstractFactory factory = UserFactory.getFactory(UserType.LIBRARIAN);

        Librarian librarian = (Librarian) factory.createUser();
        Role role1 = roleRepository.findByRole("MEMBER");
        Role role2 = roleRepository.findByRole("LIBRARIAN");

        librarian.setFullName(user.getFullName());
        librarian.setAddress(user.getAddress());
        librarian.setDisplayName(user.getDisplayName());
        librarian.setAge(user.getAge());
        librarian.setUsername(user.getUsername());
        librarian.setPassword(user.getPassword());
        librarian.setRoles(Arrays.asList(role1,role2));

        return librarianRepository.save(librarian);
    }
    @Transactional
    @Override
    public Member createMemberUser(Member member) {
        UserAbstractFactory factory = UserFactory.getFactory(UserType.MEMBER);

        Member newMember = (Member) factory.createUser();
        Role role = roleRepository.findByRole("MEMBER");

        newMember.setDisplayName(member.getDisplayName());
        newMember.setAge(member.getAge());
        newMember.setRoles(Collections.singletonList(role));

        return memberRepository.save(newMember);
    }

    @Override
    public List<UserDto> getUsers() {
        return userRepository.findAll().stream().map(
                user -> new UserDto(user.getId(),user.getUserType(),user.getDisplayName(),user.getAge())
        ).collect(Collectors.toList());
    }
}
