package com.marini.school_manager.spring_jwt.controller;

import com.marini.school_manager.spring_jwt.model.AuthUser;
import com.marini.school_manager.spring_jwt.model.ERole;
import com.marini.school_manager.spring_jwt.model.Role;
import com.marini.school_manager.spring_jwt.payload.request.LoginRequest;
import com.marini.school_manager.spring_jwt.payload.request.SignupRequest;
import com.marini.school_manager.spring_jwt.payload.response.JwtResponse;
import com.marini.school_manager.spring_jwt.payload.response.MessageResponse;
import com.marini.school_manager.spring_jwt.repository.AuthRoleRepository;
import com.marini.school_manager.spring_jwt.repository.AuthUserRepository;
import com.marini.school_manager.spring_jwt.util.security.jwt.JwtUtils;
import com.marini.school_manager.spring_jwt.util.security.service.UserDetailsImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Controller per la gestione dell'autenticazione e della registrazione.
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    /**
     * @TODO: Implementare layer service invece che usare direttamente i repository
     */
    @Autowired
    AuthUserRepository authUserRepository;

    @Autowired
    AuthRoleRepository authRoleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    /**
     * Gestisce il login degli utenti.
     */
    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles));
    }

    /**
     * Gestisce la registrazione di nuovi utenti.
     */
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {

        // Verifica se il nome utente è già in uso
        if (authUserRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        // Verifica se l'email è già registrata
        if (authUserRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        // Crea nuovo utente con password codificata
        AuthUser user = new AuthUser(
                signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()),
                signUpRequest.getFirstName(),
                signUpRequest.getLastName(),
                signUpRequest.getBirthDate()
        );

        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null || strRoles.isEmpty()) {
            // Assegna ROLE_STUDENT di default
            Role defaultRole = authRoleRepository.findByName(ERole.ROLE_STUDENT)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(defaultRole);
        } else {
            strRoles.forEach(role -> {
                switch (role.toLowerCase()) {
                    case "student":
                        Role studentRole = authRoleRepository.findByName(ERole.ROLE_STUDENT)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(studentRole);
                        break;

                    case "teacher":
                        Role teacherRole = authRoleRepository.findByName(ERole.ROLE_TEACHER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(teacherRole);
                        break;

                    default:
                        throw new RuntimeException("Error: Invalid role specified.");
                }
            });
        }

        user.setRoles(roles);
        authUserRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }
}
