package com.nutrimeal.nutrimeal_backend.service;

import com.nutrimeal.nutrimeal_backend.dto.request.LoginRequest;
import com.nutrimeal.nutrimeal_backend.dto.request.SignupRequest;
import com.nutrimeal.nutrimeal_backend.dto.response.UserInfoResponse;
import com.nutrimeal.nutrimeal_backend.entity.Role;
import com.nutrimeal.nutrimeal_backend.entity.RoleName;
import com.nutrimeal.nutrimeal_backend.entity.User;
import com.nutrimeal.nutrimeal_backend.repository.RoleRepository;
import com.nutrimeal.nutrimeal_backend.repository.UserRepository;
import com.nutrimeal.nutrimeal_backend.security.jwt.JwtProvider;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;


    public void handleRegisterUser(SignupRequest request) {
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

    public UserInfoResponse handleAuthenticateUser(LoginRequest loginRequest) {
        try {
            // when call authenticationManager.authenticate()
            // AuthenticationManager will access all AuthenticationProviders
            // =>  DaoAuthenticationProvider use UserDetailsService to check user in the database
            // check with user login does it match with the user in the database
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getEmail(),
                            loginRequest.getPassword()
                    )
            );
        }catch (AuthenticationException e){
            throw new RuntimeException("Email or password incorrect !");
        }

        User user = userRepository.findByEmail(loginRequest.getEmail()).orElseThrow();

        String accessToken = jwtProvider.generateTokenFromEmail(loginRequest.getEmail());

        String email = jwtProvider.getEmailFromJwtToken(accessToken);
//        User userDetail = userService.findByEmail(email);
//        Token jwtToken = tokenService.addToken(userDetail, accessToken, isMobileDevice(userAgent));

        List<String> roles = user.getRoles()
                .stream()
                .map(role -> role.getRoleName().name())
                .toList();

        return UserInfoResponse.builder()
                .userId(user.getUserId())
                .email(user.getEmail())
                .fullName(user.getFullName())
                .phone(user.getPhone())
                .dob(user.getDob())
                .gender(user.getGender())
                .accessToken(accessToken)
                .roles(roles)
                .avatar(user.getAvatar())
                .tokenType("Bearer")
                .point(user.getPoint())
                .build();
    }

}
