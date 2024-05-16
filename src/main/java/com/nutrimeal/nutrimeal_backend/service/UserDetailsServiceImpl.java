package com.nutrimeal.nutrimeal_backend.service;

import com.nutrimeal.nutrimeal_backend.entity.User;
import com.nutrimeal.nutrimeal_backend.exception.DomainException;
import com.nutrimeal.nutrimeal_backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> DomainException.notFound("User with email: " + email + " !"));

        return UserDetailsImpl.build(user);
    }
}
