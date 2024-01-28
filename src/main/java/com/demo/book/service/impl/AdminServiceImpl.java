package com.demo.book.service.impl;

import com.demo.book.dao.impl.UserServiceAdapter;
import com.demo.book.dto.UserDto;
import com.demo.book.entity.*;
import com.demo.book.entity.enums.UserType;
import com.demo.book.exception.UserExistsException;
import com.demo.book.factory.UserFactory.UserAbstractFactory;
import com.demo.book.factory.UserFactory.UserFactory;
import com.demo.book.repository.StaffRepository;
import com.demo.book.repository.MemberRepository;
import com.demo.book.repository.RoleRepository;
import com.demo.book.repository.UserRepository;
import com.demo.book.service.AdminService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private StaffRepository staffRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    public AdminServiceImpl() {

    }

    @Transactional
    @Override
    public void createLibrarianUser(Staff user) {
        if(staffRepository.existsByUsername(user.getUsername())) throw new UserExistsException();

        UserAbstractFactory factory = UserFactory.getFactory(UserType.STAFF);

        Staff staff = (Staff) factory.createUser();
        Role role2 = roleRepository.findByRole("ADMIN");

        staff.setFullName(user.getFullName());
        staff.setAddress(user.getAddress());
        staff.setDisplayName(user.getDisplayName());
        staff.setAge(user.getAge());
        staff.setUsername(user.getUsername());
        staff.setPhoneNumber(user.getPhoneNumber());
        staff.setPassword(passwordEncoder.encode(user.getPassword()));
        staff.setRoles(Collections.singletonList(role2));
        staffRepository.save(staff);
    }
    @Transactional
    @Override
    public void createMemberUser(Member member) {
        UserAbstractFactory factory = UserFactory.getFactory(UserType.MEMBER);

        Member newMember = (Member) factory.createUser();
        newMember.setPhoneNumber(member.getPhoneNumber());
        newMember.setDisplayName(member.getDisplayName());
        newMember.setAge(member.getAge());
        memberRepository.save(member);
    }

    @Override
    public List<UserDto> getUsers(Integer minAge,Integer maxAge,String userType) {
        AdminService userServiceAdapter = new UserServiceAdapter();
        return userServiceAdapter.getUsers(minAge,maxAge,userType);
    }
}
