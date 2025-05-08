package com.marini.school_manager.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.marini.school_manager.spring_jwt.model.AuthUser;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String specialisation;

    @OneToOne(optional = false)   // optional = false dice a JPA che il teacher deve avere sempre un User.
    @JoinColumn(
            name = "user_id",
            nullable = false      // nullable = false impone la stessa regola anche a livello di schema DB.
    )
    private AuthUser user;

    @ManyToMany
    @JoinTable(
            name = "teacher_course",
            joinColumns = @JoinColumn(name = "teacher_id"),          // chiave FK verso Teacher
            inverseJoinColumns = @JoinColumn(name = "course_id"),     // chiave FK verso Course
            uniqueConstraints = @UniqueConstraint(                   // evita duplicati nel DB.
                    columnNames = {"teacher_id", "course_id"}
            )
    )
    private List<Course> courses;

    // Aggiungi un metodo per ottenere l'username dal modello AuthUser
    @JsonProperty("username")  // per includere il campo nell'output JSON
    public String getUsername() {
        return user != null ? user.getUsername() : null;
    }

    // Per recuperare il nome
    @JsonProperty("firstName")
    public String getFirstName() {
        return user != null ? user.getFirstName() : null;
    }

    // Per recuperare il cognome
    @JsonProperty("lastName")
    public String getLastName() {
        return user != null ? user.getLastName() : null;
    }

    // Per recuperare la data di nascita
    @JsonProperty("birthDate")
    public LocalDate getBirthDate() {
        return user != null ? user.getBirthDate() : null;
    }
}
