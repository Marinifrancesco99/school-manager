package com.marini.school_manager.spring_jwt.model;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.marini.school_manager.model.Student;
import com.marini.school_manager.model.Teacher;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "username"),
                @UniqueConstraint(columnNames = "email")
        })
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 50)
    private String firstName;

    @NotBlank
    @Size(max = 50)
    private String lastName;

    @NotNull
    @Past       // Lancia un errore in caso di data uguale o successiva ad oggi.
    private LocalDate birthDate;

    @NotBlank
    @Size(max = 20)
    private String username;

    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    @NotBlank
    @Size(max = 120)
    private String password;

    // Relazioni con student e teacher
    @OneToOne(
            mappedBy = "user",
            cascade = CascadeType.ALL,     // persisti/elimini profilo insieme all’utente
            orphanRemoval = true            // se tolgo user.setStudent(null), JPA elimina il profilo
    )
    @JsonManagedReference            // Gestisce la serializzazione nel lato "forward" della relazione
    private Student student;

    @OneToOne(
            mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Teacher teacher;

    @ManyToMany(
            fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE}
    )
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    public AuthUser(String username, String email, String password, String firstName, String lastName, LocalDate birthDate) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
    }

    public AuthUser(String username, String email, String password, String firstName, String lastName, LocalDate birthDate, Role role) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.roles.add(role);
    }
}
