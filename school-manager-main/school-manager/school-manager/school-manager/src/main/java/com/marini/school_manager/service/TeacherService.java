package com.marini.school_manager.service;

import com.marini.school_manager.exception.ResourceNotFoundException;
import com.marini.school_manager.model.Teacher;
import com.marini.school_manager.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TeacherService {

    private final TeacherRepository teacherRepository;

    @Autowired
    public TeacherService(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    // Ottieni tutti gli insegnanti.
    public Page<Teacher> getAllTeacher(Pageable pageable) {
        return teacherRepository.findAll(pageable);
    }

    // Ottieni un insegnante specifico tramite l'id.
    public Teacher getTeacherById(Long id) {
        return teacherRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Teacher with Id: " + id + " not found"));
    }

    // Aggiungi un insegnante.
    public Teacher saveTeacher(Teacher teacher) {
        return teacherRepository.save(teacher);
    }

    // Elimina un insegnante
    public void deleteTeacher(Long id) {
        if (teacherRepository.existsById(id)) {
            teacherRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException("Teacher with Id: " + id + " not found");
        }
    }

    // Aggiorna l'insegnante.
    public Teacher updateTeacher(Long id, Teacher teacher) {
        Optional<Teacher> exsistingTeacherOptional = teacherRepository.findById(id);

        if (!exsistingTeacherOptional.isPresent()) {
            throw new ResourceNotFoundException("Teacher with Id: " + id + " not found");
        }

        Teacher exsistingTeacher = exsistingTeacherOptional.get();
        exsistingTeacher.setUser(teacher.getUser());
        exsistingTeacher.setCourses(teacher.getCourses());
        exsistingTeacher.setSpecialisation(teacher.getSpecialisation());

        return teacherRepository.save(exsistingTeacher);
    }
}
