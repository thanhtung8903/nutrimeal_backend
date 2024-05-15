package com.nutrimeal.nutrimeal_backend.service;

import com.nutrimeal.nutrimeal_backend.dto.request.SignupRequest;
import com.nutrimeal.nutrimeal_backend.entity.Role;
import com.nutrimeal.nutrimeal_backend.entity.RoleName;
import com.nutrimeal.nutrimeal_backend.entity.User;
import com.nutrimeal.nutrimeal_backend.repository.RoleRepository;
import com.nutrimeal.nutrimeal_backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    public void signup(SignupRequest request) {
        Set<Role> roles = new HashSet<>();
        roles.add(roleRepository.findByRoleName(RoleName.ROLE_CUSTOMER).orElseThrow(() -> new RuntimeException("Error: Role is not found.")));

        User user = new User();
        user.setRoles(roles);
        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setPoint(0);
        userRepository.save(user);

    }

}
