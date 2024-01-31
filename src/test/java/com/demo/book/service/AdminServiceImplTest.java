package com.demo.book.service;

import com.demo.book.repository.*;
import com.demo.book.service.impl.AdminServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
public class AdminServiceImplTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private StaffRepository staffRepository;
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private MemberRepository memberRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private LibraryCardRepository libraryCardRepository;
    @InjectMocks
    private AdminServiceImpl adminService;

//    @Test
//    public void
//
//    }
}
