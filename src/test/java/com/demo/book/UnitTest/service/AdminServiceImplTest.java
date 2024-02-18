package com.demo.book.UnitTest.service;

import com.demo.book.dao.impl.UserServiceAdapter;
import com.demo.book.domain.dto.UserDto;
import com.demo.book.entity.Member;
import com.demo.book.entity.Role;
import com.demo.book.entity.Staff;
import com.demo.book.entity.User;
import com.demo.book.exception.UserExistsException;
import com.demo.book.exception.UserNotFoundException;
import com.demo.book.repository.*;
import com.demo.book.service.AdminService;
import com.demo.book.service.impl.AdminServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

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

    @Test
    public void whenCreateLibrarian_Success() {
        Staff user = new Staff();
        user.setUsername("testUser");
        user.setPassword("password");

        Role librarianRole = new Role();
        librarianRole.setRole("LIBRARIAN");
        when(roleRepository.findByRole("LIBRARIAN")).thenReturn(librarianRole);

        when(staffRepository.existsByUsername("testUser")).thenReturn(false);

        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");

        adminService.createLibrarianUser(user);

        verify(staffRepository, times(1)).save(any());
    }

    @Test
    public void whenCreateLibrarian_StaffExisted_ThrowException() {
        Staff user = new Staff();
        user.setUsername("testUser");
        user.setPassword("password");

        Role librarianRole = new Role();
        librarianRole.setRole("LIBRARIAN");

        when(staffRepository.existsByUsername("testUser")).thenReturn(true);

        assertThrowsExactly(UserExistsException.class,() -> adminService.createLibrarianUser(user));

        verify(staffRepository, times(0)).save(any());
    }

    @Test
    public void whenCreateMember_Success() {
        Member member = new Member();
        member.setFullName("testUser");
        member.setEmail("user@gmail.com");
        member.setAddress("asd");
        member.setAge(25);

        adminService.createMemberUser(member);

        verify(libraryCardRepository, times(1)).save(any());
    }

    @Test
    public void getAllUser_ReturnList() {
        List<UserDto> expectedUsers = spy(new ArrayList<>());

        Integer minAge = 10;
        Integer maxAge = 30;
        String userType = "Member";

        for(int i = 0; i< 13;i++) {
            UserDto user = new UserDto();
            user.setId(i);
            user.setAge(i+10);
            user.setFullName("user " + i);
            expectedUsers.add(user);
        }

        List<UserDto> actualUsers = adminService.getUsers(minAge,maxAge,userType);

        assertEquals(expectedUsers.size(),actualUsers.size());
        assertEquals(13, expectedUsers);
    }
}
