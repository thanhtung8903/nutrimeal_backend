package com.nutrimeal.nutrimeal_backend.repository;

import com.nutrimeal.nutrimeal_backend.entity.Role;
import com.nutrimeal.nutrimeal_backend.entity.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByRoleName(RoleName roleName);
}
