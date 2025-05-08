package com.marini.school_manager.spring_jwt.controller;

import com.marini.school_manager.exception.ResourceNotFoundException;
import com.marini.school_manager.spring_jwt.model.AuthUser;
import com.marini.school_manager.spring_jwt.repository.AuthUserRepository;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final AuthUserRepository userRepository;

    @Autowired
    public UserController(AuthUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @DeleteMapping("/{id}")
    public void deleteUserById(@PathVariable Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException("User not found with id: " + id);
        }
    }

    @PutMapping("/{id}")
    public AuthUser updateUser(@PathVariable Long id, @RequestBody AuthUser user) {
        Optional<AuthUser> existingUserOptional = userRepository.findById(id);

        if (!existingUserOptional.isPresent()) {
            throw new ResourceNotFoundException("User not found with id: " + id);
        }

        AuthUser existingUser = existingUserOptional.get();
        existingUser.setUsername(user.getUsername());
        existingUser.setEmail(user.getEmail());
        existingUser.setPassword(user.getPassword());
        existingUser.setFirstName(user.getFirstName());
        existingUser.setLastName(user.getLastName());
        existingUser.setBirthDate(user.getBirthDate());

        return userRepository.save(existingUser);
    }
}
