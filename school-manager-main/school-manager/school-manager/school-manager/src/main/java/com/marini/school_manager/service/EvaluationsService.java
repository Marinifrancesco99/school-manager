package com.marini.school_manager.service;

import com.marini.school_manager.exception.ResourceNotFoundException;
import com.marini.school_manager.model.Evaluations;
import com.marini.school_manager.repository.EvaluationsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EvaluationsService {

    private final EvaluationsRepository evaluationsRepository;

    @Autowired
    public EvaluationsService(EvaluationsRepository evaluationsRepository) {
        this.evaluationsRepository = evaluationsRepository;
    }

    public Page<Evaluations> getAllEvaluations(Pageable pageable) {
        return evaluationsRepository.findAll(pageable);
    }

    public Evaluations getEvaluationsById(Long id) {
        return evaluationsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Evaluations with Id: " + id + " not found"));
    }

    public Evaluations saveEvaluations(Evaluations evaluations) {
        return evaluationsRepository.save(evaluations);
    }

    public void deleteEvaluations(Long id) {
        if (evaluationsRepository.existsById(id)) {
            evaluationsRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException("Evaluations with Id: " + id + " not found");
        }
    }

    public Evaluations updateEvaluations(Long id, Evaluations evaluations) {
        Optional<Evaluations> existingEvaluationsOptional = evaluationsRepository.findById(id);

        if (!existingEvaluationsOptional.isPresent()) {
            throw new ResourceNotFoundException("Evaluations with Id: " + id + " not found");
        }

        Evaluations existingEvaluations = existingEvaluationsOptional.get();
        existingEvaluations.setEvaluationDate(evaluations.getEvaluationDate());
        existingEvaluations.setVote(evaluations.getVote());
        existingEvaluations.setDescription(evaluations.getDescription());
        existingEvaluations.setEnrollement(evaluations.getEnrollement());

        return evaluationsRepository.save(existingEvaluations);
    }
}
