package com.nutrimeal.nutrimeal_backend.repository;

import com.nutrimeal.nutrimeal_backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String>{
}
