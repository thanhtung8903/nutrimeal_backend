package com.nutrimeal.nutrimeal_backend.service;

import com.nutrimeal.nutrimeal_backend.dto.request.SignupRequest;
import com.nutrimeal.nutrimeal_backend.entity.User;
import com.nutrimeal.nutrimeal_backend.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

//    saveUser method is missing
    public void saveUser(SignupRequest request) {
        User user = new User();
        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        userRepository.save(user);
    }

}
