package com.marini.school_manager.repository;

import com.marini.school_manager.model.Evaluations;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EvaluationsRepository extends JpaRepository<Evaluations, Long> {
}
