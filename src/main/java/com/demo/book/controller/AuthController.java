package com.demo.book.controller;

import com.demo.book.domain.response.AuthResponse;
import com.demo.book.domain.request.LoginRequest;
import com.demo.book.domain.response.RefreshTokenResponse;
import com.demo.book.entity.Permission;
import com.demo.book.entity.RefreshToken;
import com.demo.book.entity.Role;
import com.demo.book.entity.Staff;
import com.demo.book.exception.TokenRefreshException;
import com.demo.book.repository.StaffRepository;
import com.demo.book.security.JwtGenerator;
import com.demo.book.service.impl.RefreshTokenService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final StaffRepository staffRepository;
    private final JwtGenerator jwtGenerator;
    private final RefreshTokenService refreshTokenService;
    @Autowired
    public AuthController(AuthenticationManager authenticationManager, StaffRepository staffRepository, JwtGenerator jwtGenerator, RefreshTokenService refreshTokenService) {
        this.authenticationManager = authenticationManager;
        this.staffRepository = staffRepository;
        this.jwtGenerator = jwtGenerator;
        this.refreshTokenService = refreshTokenService;
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

        Staff user = (Staff) authentication.getPrincipal();

        Map<String,List<String>> roles = mapRole(user.getRoles());
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        var token = jwtGenerator.generateToken(roles,user);
        return new ResponseEntity<>(new AuthResponse(loginRequest.getUsername(),token,refreshToken.getToken()), HttpStatus.OK);
    }
    private Map<String, List<String>> mapRole(List<Role> roles){
        List<String> roleName = roles.stream().map(Role::getRole).toList();
        Map<String,List<String>> authorities = new HashMap<>();
        authorities.put("role",roleName);
        return authorities;
    }

    @GetMapping("refreshtoken/{refreshToken}")
    public ResponseEntity<?> refreshToken(@PathVariable String refreshToken) {


        return refreshTokenService.findByToken(refreshToken)
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getStaff)
                .map(user -> {
                    Map<String,List<String>> roles = mapRole(user.getRoles());
                    String token = jwtGenerator.generateToken(roles,user);
                    return ResponseEntity.ok(new RefreshTokenResponse(token, refreshToken));
                })
                .orElseThrow(() -> new TokenRefreshException(refreshToken,
                        "Refresh token is not in database!"));
    }
}
