package com.marini.school_manager.repository;

import com.marini.school_manager.model.Enrollement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnrollementRepository extends JpaRepository<Enrollement, Long> {
}
