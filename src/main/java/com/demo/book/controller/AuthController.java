package com.demo.book.controller;

import com.demo.book.dto.AuthResponse;
import com.demo.book.dto.LoginRequest;
import com.demo.book.entity.Role;
import com.demo.book.entity.Staff;
import com.demo.book.repository.StaffRepository;
import com.demo.book.security.JwtGenerator;
import org.apache.coyote.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final StaffRepository staffRepository;
    private final JwtGenerator jwtGenerator;
    @Autowired
    public AuthController(AuthenticationManager authenticationManager, StaffRepository staffRepository, JwtGenerator jwtGenerator) {
        this.authenticationManager = authenticationManager;
        this.staffRepository = staffRepository;
        this.jwtGenerator = jwtGenerator;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest){
        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()
                    )
            );
        }catch (Exception e){
            throw new BadCredentialsException("username or password is incorrect");
        }

        Staff user = staffRepository.findByUsername(loginRequest.getUsername());

        Map<String,List<String>> roles = mapRole(user.getRoles());

        SecurityContextHolder.getContext().setAuthentication(authentication);

        var token = jwtGenerator.generateToken(roles,user);
        return new ResponseEntity<>(new AuthResponse(loginRequest.getUsername(),token), HttpStatus.OK);
    }
    private Map<String, List<String>> mapRole(List<Role> roles){
        List<String> roleName = roles.stream().map(Role::getRole).toList();
        Map<String,List<String>> authorities = new HashMap<>();
        authorities.put("role",roleName);
        return authorities;
    }
}
