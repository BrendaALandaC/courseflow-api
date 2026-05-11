package com.brenda.courseflow.auth.service;

import com.brenda.courseflow.auth.dto.LoginRequest;
import com.brenda.courseflow.auth.dto.LoginResponse;
import com.brenda.courseflow.auth.dto.RegisterRequest;
import com.brenda.courseflow.role.entity.Role;
import com.brenda.courseflow.role.repository.RoleRepository;
import com.brenda.courseflow.user.entity.User;
import com.brenda.courseflow.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public void register(RegisterRequest request) {

        Role role = roleRepository.findByName("ROLE_ASSISTANT")
                .orElseThrow(() ->
                        new RuntimeException("Default role not found"));

        User user = new User();

        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(role);

        userRepository.save(user);
    }

    public LoginResponse login(LoginRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        String token =
                jwtService.generateToken(request.getUsername());

        return new LoginResponse(token);
    }
}