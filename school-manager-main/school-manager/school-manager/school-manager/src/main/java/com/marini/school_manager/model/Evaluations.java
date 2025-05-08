package com.marini.school_manager.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Evaluations {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Date evaluationDate;

    @NotNull
    private String description;

    @NotNull
    private double vote;

    @ManyToOne(optional = false)
    @JoinColumn(
            name = "enrollement_id",
            nullable = false
    )
    private Enrollement enrollement;


}
