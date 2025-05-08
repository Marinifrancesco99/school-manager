package com.marini.school_manager.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.marini.school_manager.spring_jwt.model.AuthUser;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne (optional = false)   // optional = false dice a JPA che lo student deve avere sempre un User.
    @JsonBackReference  // Ignora la serializzazione del lato "reverse" della relazione
    @JoinColumn (
            name = "user_id",
            nullable = false       // nullable = false impone la stessa regola anche a livello di schema DB.
    )
    private AuthUser user;

    @OneToOne(
            mappedBy = "student",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Enrollement enrollement;

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
