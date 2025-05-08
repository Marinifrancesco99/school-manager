package com.marini.school_manager.service;

import com.marini.school_manager.exception.ResourceNotFoundException;
import com.marini.school_manager.model.Enrollement;
import com.marini.school_manager.repository.EnrollementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EnrollementService {

    private final EnrollementRepository enrollementRepository;

    @Autowired
    public EnrollementService(EnrollementRepository enrollementRepository) {
        this.enrollementRepository = enrollementRepository;
    }

    public Page<Enrollement> getAllEnrollement(Pageable pageable) {
        return enrollementRepository.findAll(pageable);
    }

    public Enrollement getEnrollementById(Long id) {
        return enrollementRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Enrollement with Id: " + id + " not found"));
    }

    public Enrollement saveEnrollement(Enrollement enrollement) {
        return enrollementRepository.save(enrollement);
    }

    public void deleteEnrollement(Long id) {
        if (enrollementRepository.existsById(id)) {
            enrollementRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException("Enrollement with Id: " + id + " not found");
        }
    }

    public Enrollement updateEnrollement(Long id, Enrollement enrollement) {
        Optional<Enrollement> existingEnrollementOptional = enrollementRepository.findById(id);

        if (!existingEnrollementOptional.isPresent()) {
            throw new ResourceNotFoundException("Enrollement with Id: " + id + " not found");
        }

        Enrollement existingEnrollement = existingEnrollementOptional.get();
        existingEnrollement.setCourse(enrollement.getCourse());
        existingEnrollement.setEvaluations(enrollement.getEvaluations());
        existingEnrollement.setStudent(enrollement.getStudent());
        existingEnrollement.setDateOfEntry(enrollement.getDateOfEntry());

        return enrollementRepository.save(existingEnrollement);
    }
}
