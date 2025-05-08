package com.marini.school_manager.spring_jwt.repository;

import com.marini.school_manager.spring_jwt.model.ERole;
import com.marini.school_manager.spring_jwt.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthRoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(ERole name);
}
