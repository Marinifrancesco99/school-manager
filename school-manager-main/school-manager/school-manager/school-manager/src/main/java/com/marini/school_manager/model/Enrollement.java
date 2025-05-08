package com.marini.school_manager.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Enrollement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Date dateOfEntry;

    @OneToOne(optional = false)
    @JoinColumn(
            name = "student_id",
            nullable = false
    )
    private Student student;

    @OneToMany(
            mappedBy = "enrollement",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Evaluations> evaluations;

    @ManyToOne(optional = false)
    @JoinColumn(
            name = "course_id",
            nullable = false
    )
    private Course course;
}
